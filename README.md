## 数据处理

本组件支持**数据脱敏**和**数据加密**，两种功能仅需要在业务的 pojo 的字段中分别添加一个注解即可。

数据脱敏：接口的返回数据会根据脱敏规则脱敏。如：需要将正常的手机号 12345678901，显示为 123****8901；需要将正常的姓名 张三三，显示为 **三。 
 > 注意：如果您的 springboot 项目中使用默认的 jackson 序列化返回的数据，才可以使用本插件。

数据加密：存到数据库的数据经过组件的加密算法加密后，存储到数据库；查询该数据时，组件使用解密算法解密后返回。（已内置 AES 对称加密算法）
 > 注意：需要您的项目使用的是 mybatis 框架做的持久层。
 
## 快速开始

1. 引入依赖

```xml
        <dependency>
            <groupId>org.juhewu</groupId>
            <artifactId>juhewu-data-spring-boot-starter</artifactId>
            <version>1.0.0-SNAPSHOTS</version>
        </dependency>
```

2. 添加配置

```yml
juhewu:
  data:
    # 数据加密，存储到数据库的数据使用 aes 算法加密，查出来的数据使用 aes 解密
    encrypt:
      # 是否启用，默认 false，不启用
      enable: true
      # 使用此密码加密，默认是 AES 算法
      password: asdfasdfasdfasdf
      # 是否忽略解密失败，true 解密失败返回原字符，适配老数据，false 解密失败返回异常
      skip-decrypt-error: false
    # 数据脱敏
    sensitive:
      # 是否启用，默认 false，不启用
      enable: true
```

3. 在需要脱敏/加密的属性上添加注解

- @FieldEncrypt：标识该属性需要加密解密
- @FieldSensitive(sensitiveType = SensitiveType.MOBILE_PHONE)：标识该属性需要脱敏，脱敏规则使用手机号-中间 4 位替换成 * 号。更多过敏规则和自定义规则见《详细使用说明》

```java
public class Student implements Serializable {

    /**
     * 手机号
     */
    @FieldEncrypt
    @FieldSensitive(sensitiveType = SensitiveType.MOBILE_PHONE)
    private String mobilePhone;
}
```


4. 运行效果

数据加密效果：


mobile_phone 字段做了数据加密，查出来的数据也会做自动解密。

可以尝试请求示例应用：`curl localhost:8080/student/list`，在程序控制台查看 sql。

```shell
: ==>  Preparing: INSERT INTO student( `chinese_name`, `id_card`, `fixed_phone`, `mobile_phone`, `address`, `email`, `bank_card`, `password`, `key`, `test`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 
: ==> Parameters: 张三三(String), 130406199901012222(String), 0310-5251111(String), 047648d2f0832584d715845f9df4e730(String), 北京市朝阳区和平里1233号(String), 123123@126.com(String), 1234567891234567(String), qazxsw(String), 123qwe(String), test123test(String)
```

数据脱敏效果：

可以尝试请求示例应用：`curl localhost:8080/student/2`，返回的数据已经经过脱敏。

```shell
[{"id":"*","chineseName":"**三","idCard":"130406********2222","fixedPhone":"********1111","mobilePhone":"133****1234","address":"北京市朝阳区********","email":"1*****@126.com","bankCard":"123456******4567","password":"******","key":"***qwe","test":"****123****"}]    
```

## 详细使用说明

### 最新版本

最新版本为：1.0.0-SNAPSHOTS。

```xml
    <properties>
        <juhewu-data.version>1.0.0-SNAPSHOTS</mybatis-spring.version>
    </properties>
```

### 项目的 pojo 模块和业务模块是分别独立的？pojo 模块可单独引入注解模块

引入后可使用 @FieldEncrypt、@FieldSensitive

```xml
        <dependency>
            <groupId>org.juhewu</groupId>
            <artifactId>juhewu-data-annotation</artifactId>
            <version>${juhewu-data.version}</version>
            <scope>provided</scope>
        </dependency>
```

### 数据脱敏和数据加密单独使用

可分别引入：

数据脱敏
```xml
        <dependency>
            <groupId>org.juhewu</groupId>
            <artifactId>juhewu-data-sensitive-spring-boot-starter</artifactId>
            <version>${juhewu-data.version}</version>
        </dependency>
```

数据加密
```xml
        <dependency>
            <groupId>org.juhewu</groupId>
            <artifactId>juhewu-data-encrypt-spring-boot-starter</artifactId>
            <version>${juhewu-data.version}</version>
        </dependency>
```

### 数据脱敏

#### 支持的脱敏规则

1. SensitiveType.CHINESE_NAME：【中文姓名】只显示最后一个汉字，其他隐藏。比如：**三。
2. SensitiveType.ID_CARD：【身份证号】显示前六位，后四位，其他隐藏。支持 18 位或者 15 位，比如：123456***\*\*\*\*\*1234、123456*****1234
3. SensitiveType.FIXED_PHONE：【固定电话】显示后四位，其他隐藏。比如：****1234
4. SensitiveType.MOBILE_PHONE：【手机号码】显示前三位，后四位，其他隐藏。比如：123****1234
5. SensitiveType.ADDRESS：【地址】只显示前六位，不显示详细地址，比如：北京市海淀区****
6. SensitiveType.EMAIL：【电子邮箱】 邮箱前缀仅显示第一个字母，前缀其他隐藏，@及后面的地址显示，比如：a**@126.com
7. SensitiveType.BANK_CARD：【银行卡号】显示前六位，后四位，其他隐藏，比如：123456**********1234
8. SensitiveType.PASSWORD【密码】密码的全部字符都隐藏，比如：******
9. SensitiveType.KEY：【密钥】密钥除了最后三位，全部都用\*代替，比如：***xdS 脱敏后长度为6，如果明文长度不足三位，则按实际长度显示，剩余位置补\*
10. SensitiveType.DEFAULT：【默认】全部都用*代替

#### 扩展规则

```java
@Configuration
public class SensitiveStrategyConfig {

    /**
     * 添加自定义脱敏策略
     * <p>
     * 在源字符中 test 替换为 ****
     *
     * @return
     */
    @Bean
    public SensitiveStrategy sensitiveStrategy(ISensitive sensitive) {
        SensitiveStrategy sensitiveStrategy = new SensitiveStrategy();
        SensitiveSerializer.setSensitiveStrategy(sensitiveStrategy);
        SensitiveSerializer.setSensitive(sensitive);

        // 自定义策略
        sensitiveStrategy.addStrategy("test", test -> test.replace("test", "****"));
        return sensitiveStrategy;
    }
}
```

#### 当前接口不需要脱敏

针对已经加了脱敏注解的 pojo，接口中返回的是该 pojo，可以在接口返回数据前加入以下代码。

```java
        RequestDataContextHolder.skipSensitive();
```

#### 等于搜索

- 方式一：

可以在程序中调用组件的加密方法机密传入的搜索内容。

```java
// 注入组件的加密对象
private final IEncryptor encryptor;

// 加密传入的字符，返回加密后的字符串
encryptor.encrypt("13312341234");
```

- 方式二：

可以在 sql 里完成等于搜索。参考下文的模糊搜索。

#### 模糊搜索

必须使用 sql 语句完成模糊搜索。

1. 给 mybatis 添加加密密码变量，此变量需要在 sql 中解密数据时用到

```yml
mybatis:
  configuration:
    variables:
      encryptPassword: ${juhewu.data.encrypt.password}
```

2. 加密列出现在 where 条件中，需要按以下方式修改 sql

AND AES_DECRYPT(UNHEX(field),'${encryptPassword}') LIKE #{mobilePhone_like}

如：需要手机号左右模糊查询
```sql
            <if test="null != mobilePhone and '' != mobilePhone">
                <bind name="mobilePhone_like" value="'%' + mobilePhone + '%'"/>
                AND AES_DECRYPT(UNHEX(mobile_phone),'${encryptPassword}') LIKE #{mobilePhone_like}
            </if>
```