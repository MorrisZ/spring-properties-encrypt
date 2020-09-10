package com.morrisz.tools.springpropertiesencrypt;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.StringValueResolver;

/**
 * @author zhangyoumao
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    @Override
    protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess, StringValueResolver valueResolver) {
        StringValueResolver customResolver = new EncryptStringValueResolver(valueResolver);
        super.doProcessProperties(beanFactoryToProcess, customResolver);
    }
}
