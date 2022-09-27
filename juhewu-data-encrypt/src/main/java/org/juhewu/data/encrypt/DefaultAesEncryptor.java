package org.juhewu.data.encrypt;

import java.nio.charset.StandardCharsets;

import cn.hutool.crypto.symmetric.AES;

/**
 * 默认的 aes 加解密
 *
 * @author duanjw
 */
public class DefaultAesEncryptor implements IEncryptor {

    private final AES aes;

    public DefaultAesEncryptor(String password) {
        this.aes = new AES(password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 加密
     *
     * @param raw 源字符
     * @return 加密后的字符
     */
    @Override
    public String encrypt(String raw) {
        return aes.encryptHex(raw);
    }

    /**
     * 解密
     *
     * @param encode 解密后的字符
     * @return 源字符
     */
    @Override
    public String decrypt(String encode) {
        return aes.decryptStr(encode);
    }
}
