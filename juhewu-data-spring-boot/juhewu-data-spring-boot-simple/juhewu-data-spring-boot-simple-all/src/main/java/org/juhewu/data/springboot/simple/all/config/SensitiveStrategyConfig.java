package org.juhewu.data.springboot.simple.all.config;

import org.juhewu.data.sensitive.ISensitive;
import org.juhewu.data.sensitive.SensitiveSerializer;
import org.juhewu.data.sensitive.SensitiveStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 添加自定义脱敏策略
 *
 * @author duanjw
 * @since 2022/09/27
 */
@Configuration
public class SensitiveStrategyConfig {

    /**
     * 添加自定义脱敏策略
     * <p>
     * 在源字符中 test 替换为 ****
     *
     * @return
     */
    @Bean
    public SensitiveStrategy sensitiveStrategy(ISensitive sensitive) {
        // 固定，必须
        SensitiveStrategy sensitiveStrategy = new SensitiveStrategy();
        SensitiveSerializer.setSensitiveStrategy(sensitiveStrategy);
        SensitiveSerializer.setSensitive(sensitive);

        // 自定义策略，名称 test，对应 @FieldSensitive(value = "test")
        // 脱敏策略是将字符串中的 test 替换为 ****
        sensitiveStrategy.addStrategy("test", test -> test.replace("test", "****"));
        return sensitiveStrategy;
    }
}
