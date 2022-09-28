package org.juhewu.data.sensitive;

import java.util.Map;
import java.util.function.Function;

/**
 * 脱敏策略
 *
 * @author duanjw
 */
public interface ISensitiveStrategy {

    /**
     * 根据脱敏方式脱敏
     *
     * @param type 脱敏方式
     * @param raw 源内容
     * @return 脱敏后的内容
     */
    default String handle(String type, String raw) {
        return this.getStrategyFunctionMap().get(type).apply(raw);
    }

    /**
     * 获取所有脱敏方法
     *
     * @return 所有脱敏方法
     */
    Map<String, Function<String, String>> getStrategyFunctionMap();
}
