package org.juhewu.data.sensitive;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 加密策略
 *
 * @author duanjw
 * @since 2022/09/23
 */
public class SensitiveStrategy implements ISensitiveStrategy {

    private final Map<String, Function<String, String>> strategyFunctionMap;

    public SensitiveStrategy() {
        // 初始化默认的脱敏策略
        strategyFunctionMap = new HashMap<>();
        strategyFunctionMap.put(SensitiveType.CHINESE_NAME.toString(), DesensitizedUtils::chineseName);
        strategyFunctionMap.put(SensitiveType.ADDRESS.toString(), DesensitizedUtils::address);
        strategyFunctionMap.put(SensitiveType.BANK_CARD.toString(), DesensitizedUtils::bankCard);
        strategyFunctionMap.put(SensitiveType.EMAIL.toString(), DesensitizedUtils::email);
        strategyFunctionMap.put(SensitiveType.FIXED_PHONE.toString(), DesensitizedUtils::fixedPhone);
        strategyFunctionMap.put(SensitiveType.MOBILE_PHONE.toString(), DesensitizedUtils::mobilePhone);
        strategyFunctionMap.put(SensitiveType.ID_CARD.toString(), DesensitizedUtils::idCardNum);
        strategyFunctionMap.put(SensitiveType.KEY.toString(), DesensitizedUtils::key);
        strategyFunctionMap.put(SensitiveType.PASSWORD.toString(), DesensitizedUtils::password);
    }

    /**
     * 获取所有脱敏方法
     *
     * @return 所有脱敏方法
     */
    @Override
    public Map<String, Function<String, String>> getStrategyFunctionMap() {
        return strategyFunctionMap;
    }

    public SensitiveStrategy addStrategy(String key, Function<String, String> function) {
        strategyFunctionMap.put(key, function);
        return this;
    }
}