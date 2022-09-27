package org.juhewu.data.sensitive;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;

/**
 * 脱敏工具类
 *
 * @author duanjw
 * @since 2022/09/23
 */
@UtilityClass
public class DesensitizedUtils {

    /**
     * 对字符串进行脱敏操作
     *
     * @param raw 原始字符串
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @param suffixNoMaskLen 右侧需要保留几位明文字段
     * @param maskStr 用于遮罩的字符串, 如'*'
     * @return 脱敏后结果
     */
    public static String desValue(String raw, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        if (raw == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = raw.length(); i < n; i++) {
            if (i < prefixNoMaskLen) {
                sb.append(raw.charAt(i));
                continue;
            }
            if (i > (n - suffixNoMaskLen - 1)) {
                sb.append(raw.charAt(i));
                continue;
            }
            sb.append(maskStr);
        }
        return sb.toString();
    }

    /**
     * 【中文姓名】只显示最后一个汉字，其他隐藏。比如：**三
     *
     * @param fullName 姓名
     * @return 结果
     */
    public static String chineseName(String fullName) {
        if (fullName == null) {
            return null;
        }
        return desValue(fullName, 0, 1, "*");
    }

    /**
     * 【身份证号】显示前六位，后四位，其他隐藏。支持 18 位或者 15 位，比如：123456********1234、123456*****1234
     *
     * @param id 身份证号码
     * @return 结果
     */
    public static String idCardNum(String id) {
        return desValue(id, 6, 4, "*");
    }

    /**
     * 【固定电话】显示后四位，其他隐藏。比如：****1234
     *
     * @param num 固定电话
     * @return 结果
     */
    public static String fixedPhone(String num) {
        return desValue(num, 0, 4, "*");
    }

    /**
     * 【手机号码】显示前三位，后四位，其他隐藏。比如：123****1234
     *
     * @param num 手机号码
     * @return 结果
     */
    public static String mobilePhone(String num) {
        return desValue(num, 3, 4, "*");
    }

    /**
     * 【地址】只显示前六位，不显示详细地址，比如：北京市海淀区****
     *
     * @param address 地址
     * @return 结果
     */
    public static String address(String address) {
        return desValue(address, 6, 0, "*");
    }

    /**
     * 【电子邮箱】 邮箱前缀仅显示第一个字母，前缀其他隐藏，@及后面的地址显示，比如：a**@126.com
     *
     * @param email 电子邮箱
     * @return 结果
     */
    public static String email(String email) {
        if (email == null) {
            return null;
        }
        int index = CharSequenceUtil.indexOf(email, '@');
        if (index <= 1) {
            return email;
        }
        String preEmail = desValue(email.substring(0, index), 1, 0, "*");
        return preEmail + email.substring(index);

    }

    /**
     * 【银行卡号】显示前六位，后四位，其他隐藏，比如：123456**********1234
     *
     * @param cardNum 银行卡号
     * @return 结果
     */
    public static String bankCard(String cardNum) {
        return desValue(cardNum, 6, 4, "*");
    }

    /**
     * 【密码】密码的全部字符都隐藏，比如：******
     *
     * @param password 密码
     * @return 结果
     */
    public static String password(String password) {
        if (password == null) {
            return null;
        }
        return "******";
    }

    /**
     * 【密钥】密钥除了最后三位，全部都用*代替，比如：***xdS 脱敏后长度为6，如果明文长度不足三位，则按实际长度显示，剩余位置补*
     *
     * @param key 密钥
     * @return 结果
     */
    public static String key(String key) {
        if (key == null) {
            return null;
        }
        int viewLength = 6;
        StringBuilder tmpKey = new StringBuilder(desValue(key, 0, 3, "*"));
        if (tmpKey.length() > viewLength) {
            return tmpKey.substring(tmpKey.length() - viewLength);
        } else if (tmpKey.length() < viewLength) {
            int buffLength = viewLength - tmpKey.length();
            for (int i = 0; i < buffLength; i++) {
                tmpKey.insert(0, "*");
            }
            return tmpKey.toString();
        } else {
            return tmpKey.toString();
        }
    }
}