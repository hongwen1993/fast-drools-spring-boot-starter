package com.drools.core;

import com.drools.core.util.FileUtil;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/27 10:34
 * @since 1.0.0
 */
public class KieSchedule implements InitializingBean {

    private KieTemplate kieTemplate;


    public KieSchedule(KieTemplate kieTemplate) {
        this.kieTemplate = kieTemplate;
    }

    public void execute() {
        System.err.println("执行execute");
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new ruleCache(), 1, kieTemplate.getUpdate(), TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() {

    }

    private class ruleCache implements Runnable {
        @Override
        public void run() {
            // 先存入1级缓存
            String pathTotal = kieTemplate.getPath();
            String[] pathArray = pathTotal.split(KieAccessor.PATH_SPLIT);
            List<File> fileList = new ArrayList<>();
            for (String path : pathArray) {
                FileUtil.fileList(path, fileList);
            }
            for (File file : fileList) {
                String fileName = file.getName();
                String content = kieTemplate.encodeToString(file.getPath());
                kieTemplate.CACHE_RULE.put(fileName, content);
            }
            // 有Redis则存入Redis


        }
    }



}
