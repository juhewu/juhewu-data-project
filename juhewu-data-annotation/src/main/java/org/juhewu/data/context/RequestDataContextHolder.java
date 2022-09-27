package org.juhewu.data.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求数据
 *
 * @author duanjw
 * @since 2022/09/23
 */
public class RequestDataContextHolder {

    public static final String SKIP_SENSITIVE_VAR = "skipSensitive";

    private static final ThreadLocal<Map<String, Object>> REQUEST_DATA = new ThreadLocal<>();

    public static void put(Map<String, Object> requestData) {
        REQUEST_DATA.set(requestData);
    }

    public static void put(final String key, final Object value) {
        Map<String, Object> dataMap = getAll();
        if (null != dataMap && !dataMap.isEmpty()) {
            dataMap.put(key, value);
        } else {
            put(new HashMap<String, Object>(16) {

                {
                    this.put(key, value);
                }
            });
        }

    }

    public static Object get(String param) {
        Map<String, Object> dataMap = getAll();
        return null != dataMap && !dataMap.isEmpty() ? dataMap.get(param) : null;
    }

    public static Map<String, Object> getAll() {
        return (Map) REQUEST_DATA.get();
    }

    public static void remove() {
        REQUEST_DATA.remove();
    }

    /**
     * 当前线程不需要脱敏
     */
    public static void skipSensitive() {
        put(SKIP_SENSITIVE_VAR, SkipSensitive.SKIP);
    }

    /**
     * 是否不需要脱敏
     *
     * @author duanjw
     * @since 2022/09/23
     */
    public enum SkipSensitive {
        SKIP, NO_SKIP;
    }

}
