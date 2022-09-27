package org.juhewu.data.encrypt;

/**
 * 加解密
 *
 * @author duanjw
 * @since 2022/09/22
 */
public interface IEncryptor {

    /**
     * 加密
     *
     * @param raw 源字符
     * @return 加密后的字符
     */
    String encrypt(String raw);

    /**
     * 解密
     *
     * @param encode 解密后的字符
     * @return 源字符
     */
    String decrypt(String encode);
}
