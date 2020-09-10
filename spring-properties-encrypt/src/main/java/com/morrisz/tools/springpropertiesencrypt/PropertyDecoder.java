package com.morrisz.tools.springpropertiesencrypt;

/**
 * @author zhangyoumao
 */
public interface PropertyDecoder {

    /**
     *
     * @param val
     * @return
     */
    boolean supports(String val);

    /**
     *
     * @param val
     * @return
     */
    String decode(String val);
}
