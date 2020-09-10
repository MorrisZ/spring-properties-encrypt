# spring properites encrypt

## spring-properites-encrypt

配置文件属性加密工具

### 如何使用
1.生成加密key和密文
使用encrypt-tool-bin.jar，(TODO)
```
java -jar encrypt-tool-bin.jar aes key1 source1
```

参数 | 说明
- | -
arg0 | 固定为 `aes`
arg1 | AES的key
arg2 | 需加密的原文

output:
```
key: key1, source: source1 -> lGO91tuTR7WJnXixAtDF6w==
```

2.将key写入外部文件。注意此文件的私密性，**建议设置成只对启动java应用的用户可读**。

3.java应用声明解密用的bean，同时将配置项设置为密文(详见下文 [应用配置](#应用配置))
```
# {encrypt}开头的走解密通道，其他属性返回原文
db.user=foo  
db.password={encrypt}lGO91tuTR7WJnXixAtDF6w==
```

4.启动java应用，指定key文件路径
`java -Dapp.key=path_to_key_file XXXX`

### 原理
继承`PropertySourcesPlaceholderConfigurer`，遇到形如`{encrypt}XXXX`的属性值时走解密通，否则返回原文。

### 加密算法
AES/ECB/PKCS5Padding，16 byte key

key的存储路径：
- 通过启动参数指定： `-Dapp.key=path_to_key_file`
- 如果未指定启动参数，默认为 `{user.home}/app.key`
 
### 应用配置

#### maven坐标

```xml
<dependency>
    <groupId>com.morris.tools</groupId>
    <artifactId>spring-properties-encrypt</artifactId>
    <version>${version}</version>
</dependency>
```

#### 基于XML的配置

applicationContext.xml:
```xml
<bean id="propertyConfigurer" class="com.morrisz.tools.springpropertiesencrypt.EncryptPropertyPlaceholderConfigurer">
	<property name="locations">
		<list>
			<value>classpath:applicationContext.properties</value>
		</list>
	</property>
</bean>
	
<bean id="dbConfig" class="com.morris.tools.samples.DbConfig">
   <property name="username" value="${database.username}"></property>
	<property name="url" value="${database.url}"></property>
</bean>
```
applicationContext.properties:
```properties
database.url=not encrypt url
database.username={encrypt}y+sd99IRGNWUO3Nq2/f+Zg==
```

#### 基于spring boot的配置

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

手动配置DataSource：
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

`注：spring.database.password等属性不经过PropertySourcesPlaceholderConfigurer，故手动配置DataSource`

配置文件application.properties：
```properties
db.url=jdbc:oracle:thin:@localhost:1521:DBTEST
db.username=foo
db.password={encrypt}y+sd99IRGNWUO3Nq2/f+Zg==
```