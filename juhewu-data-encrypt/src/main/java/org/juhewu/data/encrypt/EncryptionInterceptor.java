package org.juhewu.data.encrypt;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.juhewu.data.annotation.FieldEncrypt;

import lombok.extern.slf4j.Slf4j;

/**
 * mybaits 新增或修改数据拦截器，对于标记了 FieldEncrypt 的属性做加密
 *
 * @author duanjw
 **/
@Slf4j
@Intercepts({
        @Signature(method = "update", type = Executor.class, args = { MappedStatement.class, Object.class })
})
public class EncryptionInterceptor implements Interceptor {

    private final IEncryptor encryptor;

    public EncryptionInterceptor(IEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    /**
     * 数据加密
     *
     * @param invocation 被调用的对象
     * @return 解密后的数据
     */
    @Override
    public Object intercept(Invocation invocation) throws IllegalAccessException, InvocationTargetException {

        Object[] args = invocation.getArgs();
        SqlCommandType sqlCommandType = null;

        for (Object object : args) {
            // 从 MappedStatement 参数中获取到操作类型
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                sqlCommandType = ms.getSqlCommandType();
                continue;
            }
            // 判断 sql 类型，insert 和 update 两种类型进行数据加密
            if (SqlCommandType.INSERT == sqlCommandType || SqlCommandType.UPDATE == sqlCommandType) {
                encryptField(object);
            }
        }
        return invocation.proceed();
    }

    /**
     * @param object 待加密的对象
     */
    private void encryptField(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            FieldEncrypt encrypt = field.getAnnotation(FieldEncrypt.class);
            Object value = field.get(object);
            if (encrypt != null && value != null) {
                String encryptString = encryptor.encrypt(value.toString());
                field.set(object, encryptString);
                if (log.isDebugEnabled()) {
                    log.debug("数据加密，加密的属性信息: 类名 {}，属性名 {}", clazz.getSimpleName(), field.getName());
                }            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
