package com.drools.core;

import com.drools.core.util.ScheduledThreadPoolExecutorUtil;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/27
 * @since 1.0.0
 */
public class KieSchedule implements InitializingBean {

    private KieTemplate kieTemplate;

    public KieSchedule(KieTemplate kieTemplate) {
        this.kieTemplate = kieTemplate;
    }

    public void execute() {
        Long update = kieTemplate.getUpdate();
        if (update == null || update == 0L) {
            update = 30L;
        }
        ScheduledThreadPoolExecutorUtil.RULE_SCHEDULE.
                scheduleAtFixedRate(new RuleCache(kieTemplate),
                1, update, TimeUnit.SECONDS);

        //ScheduledThreadPoolExecutorUtil.CACHE_KIE.scheduleAtFixedRate(new Runnable() {
        //    @Override
        //    public void run() {
        //
        //    }
        //}, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() {

    }


}
