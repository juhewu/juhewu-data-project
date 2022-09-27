package org.juhewu.data.encrypt.config;

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
@ConfigurationProperties(prefix = "juhewu.data.encrypt")
@Setter
@Getter
public class EncryptorProperties implements Serializable {

    /**
     * 加密密码
     */
    private String password;
    /**
     * 是否忽略解密失败
     *
     * true 解密失败返回原字符，可用于适配老数据
     * false 解密失败返回异常
     */
    private boolean skipDecryptError = true;
    /**
     * 是否启用，默认 false 不启用
     */
    private boolean enable;
}
