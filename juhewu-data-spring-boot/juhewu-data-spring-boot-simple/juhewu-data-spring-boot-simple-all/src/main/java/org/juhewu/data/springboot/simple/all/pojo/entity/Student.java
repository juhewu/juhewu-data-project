package org.juhewu.data.springboot.simple.all.pojo.entity;

import java.io.Serializable;

import org.juhewu.data.annotation.FieldEncrypt;
import org.juhewu.data.annotation.FieldSensitive;
import org.juhewu.data.sensitive.SensitiveType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生实体类
 *
 * @author duanjw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    /**
     * id
     */
    @FieldSensitive
    private String id;
    /**
     * 姓名
     */
    @FieldSensitive(sensitiveType = SensitiveType.CHINESE_NAME)
    private String chineseName;

    /**
     * 身份证
     */
    @FieldSensitive(sensitiveType = SensitiveType.ID_CARD)
    private String idCard;
    /**
     * 座机
     */
    @FieldSensitive(sensitiveType = SensitiveType.FIXED_PHONE)
    private String fixedPhone;
    /**
     * 手机号
     */
    @FieldEncrypt
    @FieldSensitive(sensitiveType = SensitiveType.MOBILE_PHONE)
    private String mobilePhone;
    /**
     * 地址
     */
    @FieldSensitive(sensitiveType = SensitiveType.ADDRESS)
    private String address;
    /**
     * 邮箱
     */
    @FieldSensitive(sensitiveType = SensitiveType.EMAIL)
    private String email;
    /**
     * 银行卡号
     */
    @FieldSensitive(sensitiveType = SensitiveType.BANK_CARD)
    private String bankCard;
    /**
     * 密码
     */
    @FieldSensitive(sensitiveType = SensitiveType.PASSWORD)
    private String password;
    /**
     * 密钥
     */
    @FieldSensitive(sensitiveType = SensitiveType.KEY)
    private String key;

    /**
     * test
     * 只用自定义的脱敏策略
     */
    @FieldSensitive(value = "test")
    private String test;
}
