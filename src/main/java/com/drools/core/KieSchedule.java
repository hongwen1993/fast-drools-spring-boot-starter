package com.drools.core;

import com.drools.core.util.ScheduledThreadPoolExecutorUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/27 10:34
 * @since 1.0.0
 */
public class KieSchedule {

    private KieTemplate kieTemplate;
    private ApplicationContext applicationContext;

    public KieSchedule(KieTemplate kieTemplate, ApplicationContext applicationContext) {
        this.kieTemplate = kieTemplate;
        if (applicationContext != null) {
            this.applicationContext = applicationContext;
        }
    }

    public void execute() {
        Long update = kieTemplate.getUpdate();
        if (update == null || update == 0L) {
            update = 30L;
        }
        ScheduledThreadPoolExecutorUtil.RULE_SCHEDULE.
                scheduleAtFixedRate(new RuleCache(kieTemplate, applicationContext),
                1, update, TimeUnit.SECONDS);
    }

}
