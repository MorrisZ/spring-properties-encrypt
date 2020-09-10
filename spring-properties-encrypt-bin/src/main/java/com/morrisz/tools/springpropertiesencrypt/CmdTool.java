package com.morrisz.tools.springpropertiesencrypt;

/**
 * @author zhangyoumao
 */
public class CmdTool {

    public static void main(String[] args) throws Exception {
        String alg = args[0];
        if ("AES".equalsIgnoreCase(alg)) {
            String key = args[1];
            String source = args[2];
            String encrypt = AESUtils.encrypt(key, source);
            System.out.printf("key: %s, source: %s -> %s", key, source, encrypt);
            System.out.println();
        } else {
            throw new UnsupportedOperationException("unsupported operation");
        }
    }
}
