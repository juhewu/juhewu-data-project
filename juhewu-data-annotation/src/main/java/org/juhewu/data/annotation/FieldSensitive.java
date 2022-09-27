package org.juhewu.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.juhewu.data.sensitive.SensitiveSerializer;
import org.juhewu.data.sensitive.SensitiveType;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 对象脱敏注解
 *
 * @author duanjw
 * @since 2022/09/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerializer.class)
public @interface FieldSensitive {

    /**
     * 脱敏数据类型, 非Customer时, 将忽略 refixNoMaskLen 和 suffixNoMaskLen 和 maskStr
     */
    SensitiveType sensitiveType() default SensitiveType.DEFAULT;

    /**
     * 左侧需要保留几位明文字段
     */
    int prefixNoMaskLen() default 0;

    /**
     * 右侧需要保留几位明文字段
     */
    int suffixNoMaskLen() default 0;

    /**
     * 用于遮罩的字符串, 如'*'
     */
    String maskStr() default "*";

    /**
     * 自定义的加密方式，如果有则优先使用自定义的加密方式
     * 通过 SensitiveStrategy 设置
     *
     * @return
     */
    String value() default "";

}
