package org.juhewu.data.sensitive.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 加密
 *
 * @author duanjw
 * @since 2022/09/22
 */
@ConfigurationProperties(prefix = "juhewu.data.sensitive")
@Setter
@Getter
public class SensitiveProperties implements Serializable {
    /**
     * 是否启用，默认 false 不启用
     */
    private boolean enable;
}
