package com.morrisz.tools.samples;

import com.morrisz.tools.springpropertiesencrypt.EncryptPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyoumao
 */
@Configuration
public class PlaceHolderConfig {

    @Bean
    public static BeanFactoryPostProcessor encryptPropertyPlaceHolderConfigure() {
        return new EncryptPropertyPlaceholderConfigurer();
    }
}
