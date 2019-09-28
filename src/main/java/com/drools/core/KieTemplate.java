package com.drools.core;

import com.drools.core.util.FileUtil;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.BeanClassLoaderAware;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/26
 * @since 1.0.0
 */
public class KieTemplate extends KieAccessor implements BeanClassLoaderAware {

    /**
     * 如果没有分布式的缓存工具，则使用本地缓存
     */
    public Map<String, String> CACHE_RULE = new ConcurrentHashMap<>();

    private ClassLoader classLoader;

    public KieTemplate() {
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    /**
     * 根据文件名获取KieSession
     * @param fileName  文件名，可以输入多个（需要带后缀）
     * @return  KieSession
     */
    public KieSession getKieSession(String... fileName) {
        List<String> ds = new ArrayList<>();
        for (String name : fileName) {
            ds.add(CACHE_RULE.get(name));
        }
        if (ds.isEmpty()) {
            doRead0();
        }
        return decodeToSession(ds.toArray(new String[]{}));
    }

    /**
     * 规则文件，决策表解析成字符串
     * @param realPath  决策表路径
     * @return  字符串
     */
    public String encodeToString(String realPath) {
        File file = new File(realPath);
        if (!file.exists()) {
            return null;
        }
        // drl文件
        if (realPath.endsWith("drl")) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(realPath, "r");
                FileChannel channel = raf.getChannel();
                MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
                CharBuffer charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
                return charBuffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (raf != null) {
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("file not fount.");
        }
        // xls文件
        if (realPath.endsWith("xls")) {
            return new SpreadsheetCompiler().compile(is, InputType.XLS);
        }
        // csv文件
        if (realPath.endsWith("csv")) {
            return new SpreadsheetCompiler().compile(is, InputType.CSV);
        }
        return null;
    }

    /**
     * 把字符串解析成KieSession
     * @param drl   规则文件字符串
     * @return  KieSession
     */
    public KieSession decodeToSession(String... drl) {
        KieHelper kieHelper = new KieHelper();
        for (String s : drl) {
            kieHelper.addContent(s, ResourceType.DRL);
        }
        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                logger.error("Error: {}", message.getText());
            }
            throw new IllegalStateException("Compilation errors.");
        }
        KieBaseConfiguration config = kieHelper.ks.newKieBaseConfiguration();
        if ("stream".equalsIgnoreCase(getMode())) {
            config.setOption(EventProcessingOption.STREAM);
        } else {
            config.setOption(EventProcessingOption.CLOUD);
        }
        KieBase kieBase = kieHelper.build(config);
        return kieBase.newKieSession();
    }

    /**
     * 获取绝对路径下的规则文件对应的KieBase
     * @param classPath     绝对路径/文件目录
     * @return KieBase
     */
    public KieBase getKieBase(String classPath) throws Exception {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        Resource resource = ResourceFactory.newFileResource(classPath);
        kfs.write(resource);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        if (kieBuilder.getResults().getMessages(Message.Level.ERROR).size() > 0) {
            throw new Exception();
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository()
                .getDefaultReleaseId());
        return kieContainer.getKieBase();
    }

    /**
     * 私有，do开头，0结尾的方法全部为私有
     */
    public void doRead0() {
        // 先存入1级缓存
        String pathTotal = getPath();
        String[] pathArray = pathTotal.split(KieAccessor.PATH_SPLIT);
        List<File> fileList = new ArrayList<>();
        for (String path : pathArray) {
            FileUtil.fileList(path, fileList);
        }
        for (File file : fileList) {
            String fileName = file.getName();
            String content = encodeToString(file.getPath());
            CACHE_RULE.put(fileName, content);
        }
        // 有Redis则存入Redis

    }


}
