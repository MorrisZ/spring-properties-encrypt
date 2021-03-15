# spring properties encrypt

spring配置文件属性加密工具

## 如何使用

1. 构建

```bash
cd ${project_root}
mvn clean install -DskipTests
```

2. 指定key，生成密文

```bash
cd spring-properties-encrypt-bin/target
java -jar spring-properties-encrypt-bin.jar aes key1 source1

# output
key: key1, source: source1 -> lGO91tuTR7WJnXixAtDF6w==
```

参数|说明
-|-
arg0|固定为 `aes`
arg1|AES的key
arg2|需加密的原文

output:
```
# 加密后为lGO91tuTR7WJnXixAtDF6w==
key: key1, source: source1 -> lGO91tuTR7WJnXixAtDF6w==
```

3. 将key文本写入外部文件。注意此文件的私密性，**建议设置成只对启动java应用的用户可读**。

4. 应用配置

maven坐标：
```xml
<dependency>
    <groupId>com.morris.tools</groupId>
    <artifactId>spring-properties-encrypt</artifactId>
    <version>${version}</version>
</dependency>
```

注册BeanFactoryPostProcessor:
```java
@Configuration
public class PlaceHolderConfig {

    @Bean
    public static BeanFactoryPostProcessor encryptPropertyPlaceHolderConfigure() {
        return new EncryptPropertyPlaceholderConfigurer();
    }
}
```

DataSource配置：
```java
@Configuration
public class DataSourceConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String userName;

    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }
}
```

配置文件application.properties：
```properties
# {encrypt}开头的走解密通道，其他属性返回原文
db.url=jdbc:mysql://localhost:3306/test
db.username=foo
db.password={encrypt}y+sd99IRGNWUO3Nq2/f+Zg==
```

`注：spring.database.password等属性不经过PropertySourcesPlaceholderConfigurer，故手动配置DataSource`

5. 启动java应用，指定key文件路径

`java -Dapp.key=path_to_key_file XXXX`

key文件的路径：
- 通过启动参数指定： `-Dapp.key=path_to_key_file`
- 如果未指定启动参数，默认为 `{user.home}/app.key`

## 原理
继承`PropertySourcesPlaceholderConfigurer`，遇到形如`{encrypt}XXXX`的属性值时走解密通，否则返回原文。

### 加密算法
AES/ECB/PKCS5Padding，16 byte key
