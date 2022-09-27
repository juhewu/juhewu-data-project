package org.juhewu.data.encrypt;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.juhewu.data.annotation.FieldEncrypt;
import org.juhewu.data.exception.DecryptException;

import lombok.extern.slf4j.Slf4j;

/**
 * mybaits 查询数据拦截器，对于标记了 FieldEncrypt 的属性做加密
 *
 * @author duanjw
 * @since 2022/09/21
 **/
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
                BoundSql.class }),
})
public class DecryptionInterceptor implements Interceptor {

    private final IEncryptor encryptor;
    private final boolean isSkipDecryptError;

    public DecryptionInterceptor(IEncryptor encryptor, boolean isSkipDecryptError) {
        this.encryptor = encryptor;
        this.isSkipDecryptError = isSkipDecryptError;
    }

    /**
     * 数据加密
     *
     * @param invocation 待解密的对象
     * @return 加密后的数据
     */
    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        Object result = invocation.proceed();
        if (result instanceof ArrayList) {
            @SuppressWarnings("rawtypes")
            ArrayList list = (ArrayList) result;
            if (list.isEmpty()) {
                return result;
            }
            for (Object item : list) {
                decryptField(item);
            }
            return result;
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * @param object 待检查的对象
     */
    private void decryptField(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            FieldEncrypt encrypt = field.getAnnotation(FieldEncrypt.class);
            Object value = field.get(object);
            if (encrypt != null && value != null) {
                try {
                    String decryptString = encryptor.decrypt(value.toString());
                    field.set(object, decryptString);
                } catch (Exception e) {
                    if (!isSkipDecryptError) {
                        log.error("数据解密失败，解密的属性信息: 类名 {}，属性名 {}", clazz.getSimpleName(), field.getName());
                        throw new DecryptException("数据解密出现错误。", e);
                    }
                    log.debug("数据解密失败返回源字符，解密的属性信息: 类名 {}，属性名 {}", clazz.getSimpleName(), field.getName());
                    return;
                }
                if (log.isDebugEnabled()) {
                    log.debug("数据解密，解密的属性信息: 类名 {}，属性名 {}", clazz.getSimpleName(), field.getName());
                }
            }
        }
    }
}
