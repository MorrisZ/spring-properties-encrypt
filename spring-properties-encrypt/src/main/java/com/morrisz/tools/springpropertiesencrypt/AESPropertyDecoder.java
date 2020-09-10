package com.morrisz.tools.springpropertiesencrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Scanner;


/**
 * value特征： {encrypt}ABCDEF
 * key值从文件读取，路径由systemProperty: app.key指定
 *
 * @author zhangyoumao
 */
public class AESPropertyDecoder implements PropertyDecoder {

    private static final String PATH_PROP_KEY = "app.key";
    private static final String USER_HOME_KEY_FILE = "app.key";
    private static final Logger log = LoggerFactory.getLogger(AESPropertyDecoder.class);
    private final static String key;
    private static final String PREFIX = "{encrypt}";
    
    static {
        key = getKey();
    }

    private static String getKey() {
        String path = System.getProperty(PATH_PROP_KEY);
        if (path != null) {
            File keyFile = new File(path);
            if (keyFile.exists()) {
                return readFile(keyFile);
            } else {
                log.error("key file does NOT exist");
            }
        } else {
            // get from user.home
            String userHome = System.getProperty("user.home");
            if (userHome != null) {
                File dir = new File(userHome);
                File keyFile = new File(dir, USER_HOME_KEY_FILE);
                if (keyFile.exists()) {
                    return readFile(keyFile);
                } else {
                    log.error("key file does NOT exist");
                }
            }
        }
        return null;
    }

    private static String readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (Exception ex) {
            log.error("error reading key file", ex);
        }
        return null;
    }

    @Override
    public boolean supports(String val) {
        return val.startsWith(PREFIX);
    }

    @Override
    public String decode(String val) {
        if (key == null) {
            log.error("can NOT decode due to absent key!");
            return val;
        }
        // strip "{encrypt}"
        String source = val.substring(9);

        try {
            return AESUtils.decrypt(key, source);
        } catch (Exception ex) {
            log.error("error decrypting", ex);
        }
        // falls back to original value if failed
        return val;
    }
}
