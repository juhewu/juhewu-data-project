package org.juhewu.data.sensitive.config;

import org.juhewu.data.sensitive.DefaultSensitive;
import org.juhewu.data.sensitive.ISensitive;
import org.juhewu.data.sensitive.RequestDataFilter;
import org.juhewu.data.sensitive.SensitiveSerializer;
import org.juhewu.data.sensitive.SensitiveStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 脱敏自动配置
 *
 * @author duanjw
 * @since 2022/09/23
 */
@Configuration
@Import(SensitiveProperties.class)
@ConditionalOnProperty(prefix = "juhewu.data.sensitive", value = "enable", havingValue = "true")
public class SensitiveAutoConfiguration {

    /**
     * 默认脱敏
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ISensitive sensitive() {
        return new DefaultSensitive();
    }

    /**
     * 脱敏策略
     *
     * @param sensitive 默认的脱敏
     * @return 脱敏策略
     */
    @Bean
    @ConditionalOnMissingBean
    public SensitiveStrategy sensitiveStrategy(ISensitive sensitive) {
        SensitiveStrategy sensitiveStrategy = new SensitiveStrategy();
        SensitiveSerializer.setSensitiveStrategy(sensitiveStrategy);
        SensitiveSerializer.setSensitive(sensitive);
        return sensitiveStrategy;
    }

    /**
     * 请求数据 filter，请求结束后，清空
     * @return RequestDataFilter
     */
    @Bean
    public RequestDataFilter requestDataFilter() {
        return new RequestDataFilter();
    }
}
