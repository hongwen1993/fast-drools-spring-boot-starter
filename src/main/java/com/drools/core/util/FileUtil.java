package com.drools.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/27
 * @since 1.0.0
 */
public class FileUtil {

    private static Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 临时存放规则文件的文件夹
     */
    public static final String TEMP_DIR;

    static {
        boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");
        if (isLinux) {
            TEMP_DIR = "/tmp";
        } else {
            Map<String, String> map = System.getenv();
            String win = map.get("TEMP");
            if (win != null && win.length() > 0) {
                TEMP_DIR = map.get("TEMP");
            } else {
                TEMP_DIR = "temp";
            }
        }
    }

    /**
     * 整合所有文件，到所传的List中
     *
     * @param path     可能含有classpath形式，因此需要进行判断
     * @param fileList {@code List<File>} 所传入的FileList
     */
    public static void fileList(String path, List<File> fileList) {
        if (path.startsWith(ResourceLoader.CLASSPATH_URL_PREFIX)) {
            try {
                createTempDir();
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources(path);
                for (Resource resource : resources) {
                    fileList.add(copyResourceToTempFile(resource));
                }
            } catch (IOException e) {
                LOG.error("资源读取异常！", e);
            }
        } else {
            File file = new File(path);
            if (file.exists()) {
                if (!file.isDirectory()) {
                    // base case
                    fileList.add(file);
                }
                if (file.isDirectory()) {
                    File[] f = file.listFiles();
                    if (f != null) {
                        for (File nextFile : f) {
                            fileList(nextFile.getPath(), fileList);
                        }
                    }
                }
            }
        }
    }

    /**
     * 将资源文件转为临时文件
     *
     * @param resource 资源目录下的文件，需要copy出来，因为打包后可能导致资源路径不正确。
     * @return {@code File} 资源目录对应的文件，但不是实际文件，是拷贝的。
     */
    public static File copyResourceToTempFile(Resource resource) {
        InputStream is = null;
        OutputStream os = null;
        File targetFile = null;
        try {
            is = resource.getInputStream();
            byte[] buffer = new byte[is.available()];
            int n = is.read(buffer);
            targetFile = new File(TEMP_DIR + File.separator + resource.getFilename());
            os = new FileOutputStream(targetFile);
            os.write(buffer);
        } catch (Exception e) {
            LOG.error("资源整合失败", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                LOG.error("无法关闭文件", e);
            }
        }
        return targetFile;
    }

    /**
     * 创建临时目录文件夹
     */
    public static void createTempDir() {
        File file = new File(TEMP_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
    }


}
