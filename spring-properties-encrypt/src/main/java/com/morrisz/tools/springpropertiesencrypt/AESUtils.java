package com.morrisz.tools.springpropertiesencrypt;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zhangyoumao
 */
public class AESUtils {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     *
     * @param key
     * @param source
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String source) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(initKey(key), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] bytes = cipher.doFinal(source.getBytes());
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     *
     * @param key
     * @param source
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String source) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(initKey(key), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(source.getBytes()));
        return new String(bytes);
    }


    /**
     * 最多支持16 byte，截断或补0
     *
     * @param keyStr
     * @return
     */
    private static byte[] initKey(String keyStr) {
        byte[] bytes = keyStr.getBytes();
        byte[] result = new byte[16];
        int length = Math.min(keyStr.length(), 16);
        for(int i = 0; i < length; i++) {
            result[i] = bytes[i];
        }
        // padding 0
        length = 16 - keyStr.length();
        for(int i = 0; i < length; i++) {
            result[16 - 1 - i] = 0;
        }
        return result;
    }
}
