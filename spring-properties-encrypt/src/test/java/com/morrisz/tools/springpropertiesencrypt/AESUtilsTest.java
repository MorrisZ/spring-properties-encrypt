package com.morrisz.tools.springpropertiesencrypt;

import com.morrisz.tools.springpropertiesencrypt.AESUtils;
import org.junit.Test;

/**
 * @author zhangyoumao
 */
public class AESUtilsTest {

    @Test
    public void encrypt() throws Exception {
        String encoded = AESUtils.encrypt("hello", "world");
        System.out.println("encoded: "+ encoded);

        String decrypt = AESUtils.decrypt("hello", encoded);
        System.out.println("decoded: " + decrypt);
    }
}