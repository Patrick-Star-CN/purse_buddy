package team.delete.pursebuddy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Component
@ConfigurationProperties("x-ti")
public class XTiConfig {
    /**
     * 设置合合账号的appid
     */
    private static String appid;

    /**
     * 设置合合账号的Secret
     */
    private static String secret;

    public void setAppid(String appid) {
        XTiConfig.appid = appid;
    }

    public void setSecret(String secret) {
        XTiConfig.secret = secret;
    }

    public static String getAppid() {
        return appid;
    }

    public static String getSecret() {
        return secret;
    }
}
