package org.juhewu.data.sensitive;

import java.io.IOException;
import java.util.Objects;

import org.juhewu.data.annotation.FieldSensitive;
import org.juhewu.data.context.RequestDataContextHolder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import lombok.AllArgsConstructor;

/**
 * 返回的 json 数据脱敏
 *
 * @author duanjw
 * @since 2022/09/23
 */
@AllArgsConstructor
public class SensitiveSerializer extends JsonSerializer<String> implements ContextualSerializer {

    public SensitiveSerializer() {
    }

    private String type;
    private SensitiveType sensitiveType;

    private Integer prefixNoMaskLen;

    private Integer suffixNoMaskLen;

    private String maskStr;

    private static ISensitive SENSITIVE;
    private static ISensitiveStrategy SENSITIVE_STRATEGY;

    public static void setSensitive(ISensitive sensitive) {
        SENSITIVE = sensitive;
    }

    public static void setSensitiveStrategy(ISensitiveStrategy sensitiveStrategy) {
        SENSITIVE_STRATEGY = sensitiveStrategy;
    }

    /**
     * 脱敏数据
     *
     * @param value
     * @param gen
     * @param serializers
     * @throws IOException
     */
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (null == SENSITIVE_STRATEGY) {
            gen.writeObject(value);
        } else {
            // 不需要脱敏
            if (Objects.equals(RequestDataContextHolder.SkipSensitive.SKIP, RequestDataContextHolder.get(RequestDataContextHolder.SKIP_SENSITIVE_VAR))) {
                gen.writeObject(value);
                return;
            }
            // 使用指定的 type
            if(!"".equals(type)) {
                gen.writeObject(SENSITIVE_STRATEGY.handle(type,value));
                return;
            }
            // 默认的脱敏方式
            if (sensitiveType == SensitiveType.DEFAULT) {
                gen.writeObject(SENSITIVE.sensitive(value, prefixNoMaskLen, suffixNoMaskLen, maskStr));
                return;
            }

            gen.writeObject(SENSITIVE_STRATEGY.handle(sensitiveType.toString(), value));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
            final BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                FieldSensitive sensitive = beanProperty.getAnnotation(FieldSensitive.class);
                if (sensitive == null) {
                    sensitive = beanProperty.getContextAnnotation(FieldSensitive.class);
                }
                if (sensitive != null) {
                    return new SensitiveSerializer(sensitive.value(), sensitive.sensitiveType(), sensitive.prefixNoMaskLen(),
                            sensitive.suffixNoMaskLen(), sensitive.maskStr());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}