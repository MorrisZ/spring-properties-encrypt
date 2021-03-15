# spring-properties-encrypt

spring properties encrypt tool

## how to use

1. build from source

```bash
cd ${project_root}
mvn clean install -DskipTests
```

2. generate encrypted properties

```bash
cd spring-properties-encrypt-bin/target
java -jar spring-properties-encrypt-bin.jar aes key1 source1

# output
key: key1, source: source1 -> lGO91tuTR7WJnXixAtDF6w==
```

arguments|description
-|-
arg0|only `aes` is supported
arg1|key of AES alg
arg2|source text

output:
```
key: key1, source: source1 -> lGO91tuTR7WJnXixAtDF6w==
```

3. write key text to a file, eg.`app.key`.
This file should be private, and readable only to the user starting JVM.
 
4. Java Code

maven dependency：
```xml
<dependency>
    <groupId>com.morris.tools</groupId>
    <artifactId>spring-properties-encrypt</artifactId>
    <version>${version}</version>
</dependency>
```

register BeanFactoryPostProcessor:
```java
@Configuration
public class PlaceHolderConfig {

    @Bean
    public static BeanFactoryPostProcessor encryptPropertyPlaceHolderConfigure() {
        return new EncryptPropertyPlaceholderConfigurer();
    }
}
```

DataSource Bean：
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

application.properties：
```properties
# properties starting with {encrypt} will be decrypted
db.url=jdbc:mysql://localhost:3306/test
db.username=foo
db.password={encrypt}y+sd99IRGNWUO3Nq2/f+Zg==
```

5. Run your application, with key file path specified 

`java -Dapp.key=path_to_key_file XXXX`

if `app.key` is not specified when starting up, the default value would be `{user.home}/app.key`

