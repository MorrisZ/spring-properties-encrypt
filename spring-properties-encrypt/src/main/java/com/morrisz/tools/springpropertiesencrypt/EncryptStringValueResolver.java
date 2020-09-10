package com.morrisz.tools.springpropertiesencrypt;

import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import java.util.LinkedList;
import java.util.List;

/**
 * decorator for StringValueResolver
 *
 * @author zhangyoumao
 */
public class EncryptStringValueResolver implements StringValueResolver {

    private StringValueResolver stringValueResolver;

    private List<PropertyDecoder> propertyDecoders;

    public EncryptStringValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
        // default decoder
        propertyDecoders = new LinkedList<PropertyDecoder>();
        propertyDecoders.add(new AESPropertyDecoder());
    }

    @Override
    public String resolveStringValue(String strVal) {
        String result = stringValueResolver.resolveStringValue(strVal);

        if (StringUtils.hasText(result)) {
            for (PropertyDecoder propertyDecoder : propertyDecoders) {
                if (propertyDecoder.supports(result)) {
                    result = propertyDecoder.decode(result);
                    break;
                }
            }
        }
        return result;
    }
}
