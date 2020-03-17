package org.springframework.boot.autoconfigure.data.redis;

import com.drools.core.KieSchedule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存中心
 *
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2020/3/12
 * @since 1.0.0
 */

@Configuration
@ConditionalOnClass(name = "org.springframework.data.redis.core.RedisOperations")
@AutoConfigureBefore(KieSchedule.class)
public class CacheCenterAutoConfiguration {

    static {
        System.out.println("加载CacheCenterAutoConfiguration");
    }
}
