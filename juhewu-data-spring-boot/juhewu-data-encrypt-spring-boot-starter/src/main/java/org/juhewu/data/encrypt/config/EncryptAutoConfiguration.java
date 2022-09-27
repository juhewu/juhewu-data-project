package org.juhewu.data.encrypt.config;

import org.juhewu.data.encrypt.DecryptionInterceptor;
import org.juhewu.data.encrypt.DefaultAesEncryptor;
import org.juhewu.data.encrypt.EncryptionInterceptor;
import org.juhewu.data.encrypt.IEncryptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;

/**
 * 加解密自动配置
 *
 * @author duanjw
 * @since 2022/09/23
 */
@Slf4j
@Configuration
@Import(EncryptorProperties.class)
@ConditionalOnProperty(prefix = "juhewu.data.encrypt", value = "enable", havingValue = "true")
public class EncryptAutoConfiguration {

    /**
     * 默认的加解密
     *
     * @param encryptorProperties 配置
     * @return
     */
    @Bean
    public IEncryptor defaultAesEncryptor(EncryptorProperties encryptorProperties) {
        log.debug("使用默认的 aes 算法加解密字段属性的值");
        return new DefaultAesEncryptor(encryptorProperties.getPassword());
    }

    /**
     * mybatis 解密拦截器
     * @param encryptor encryptor
     * @return
     */
    @Bean
    public DecryptionInterceptor decryptionInterceptor(IEncryptor encryptor, EncryptorProperties properties) {
        return new DecryptionInterceptor(encryptor, properties.isSkipDecryptError());
    }

    /**
     * mybatis 加密拦截器
     * @param encryptor encryptor
     * @return
     */
    @Bean
    public EncryptionInterceptor encryptionInterceptor(IEncryptor encryptor) {
        return new EncryptionInterceptor(encryptor);
    }
}
