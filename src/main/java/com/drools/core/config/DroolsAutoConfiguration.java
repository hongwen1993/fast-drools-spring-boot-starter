package com.drools.core.config;

import com.drools.core.KieSchedule;
import com.drools.core.KieTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.CacheCenterAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/26 16:27
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(DroolsProperties.class)
public class DroolsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "kieTemplate")
    public KieTemplate kieTemplate(DroolsProperties droolsProperties) {
        KieTemplate kieTemplate = new KieTemplate();
        kieTemplate.setPath(droolsProperties.getPath());
        kieTemplate.setMode(droolsProperties.getMode());
        kieTemplate.setUpdate(droolsProperties.getUpdate());
        return kieTemplate;
    }



    @Bean
    @ConditionalOnMissingBean(CacheCenterAutoConfiguration.class)
    public KieSchedule kieSchedule(KieTemplate kieTemplate, ApplicationContext applicationContext) {
        System.out.println("kieSchedule");
        KieSchedule kieSchedule = new KieSchedule(kieTemplate, applicationContext);
        kieSchedule.execute();
        return kieSchedule;
    }

}
