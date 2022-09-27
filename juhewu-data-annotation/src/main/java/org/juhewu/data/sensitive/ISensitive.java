package org.juhewu.data.sensitive;

/**
 * 脱敏
 *
 * @author duanjw
 * @since 2022/09/23
 */
public interface ISensitive {

    /**
     * 脱敏
     *
     * @param raw 源内容
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @param suffixNoMaskLen 右侧需要保留几位明文字段
     * @param maskStr 用于遮罩的字符串, 如'*'
     * @return 脱敏后的内容
     */
    String sensitive(String raw, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr);
}
