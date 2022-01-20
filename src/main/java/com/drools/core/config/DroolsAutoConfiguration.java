package com.drools.core.config;

import com.drools.core.KieSchedule;
import com.drools.core.KieTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

import static com.drools.core.common.Constants.LISTENER_CLOSE;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/26
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
        String autoUpdate = droolsProperties.getAutoUpdate();
        if (Objects.equals(LISTENER_CLOSE, autoUpdate)) {
            // 关闭自动更新
            kieTemplate.setUpdate(999999L);
        } else {
            // 启用自动更新
            kieTemplate.setUpdate(droolsProperties.getUpdate());
        }
        kieTemplate.setListener(droolsProperties.getListener());
        kieTemplate.setVerify(droolsProperties.getVerify());
        String charset = droolsProperties.getCharset();
        if (StringUtils.isNotBlank(charset)) {
            kieTemplate.setCharset(charset);
        }
        return kieTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(name = "kieSchedule")
    public KieSchedule kieSchedule(KieTemplate kieTemplate) {
        KieSchedule kieSchedule = new KieSchedule(kieTemplate);
        kieSchedule.execute();
        return kieSchedule;
    }

}
