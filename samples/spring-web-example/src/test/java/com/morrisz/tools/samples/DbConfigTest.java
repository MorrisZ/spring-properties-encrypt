package com.morrisz.tools.samples;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * @author zhangyoumao
 */
public class DbConfigTest {

    /**
     * use app.key in {user.home} or -Dapp.key=path to app key
     * key: itsection
     */
    @Test
    public void startUpContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DbConfig config = context.getBean("dbConfig", DbConfig.class);
        System.out.println(config.getUrl());
        System.out.println(config.getUsername());
        assertEquals("foo", config.getUsername());
    }
}