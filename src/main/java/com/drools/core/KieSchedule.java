package com.drools.core;

import com.drools.core.util.ThreadPoolExecutorUtil;
import org.springframework.beans.factory.InitializingBean;

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
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Long update = kieTemplate.getUpdate();
        if (update == null || update == 0L) {
            update = 30L;
        }
        service.scheduleAtFixedRate(new RuleCache(kieTemplate), 1, update, TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() {

    }


}
