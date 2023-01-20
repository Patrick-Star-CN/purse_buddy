package team.delete.pursebuddy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Component
@ConfigurationProperties("wechat")
public class WechatConfig {

    /**
     * 设置微信小程序的appid
     */
    private static String appid;

    /**
     * 设置微信小程序的Secret
     */
    private static String secret;

    public void setAppid(String appid) {
        WechatConfig.appid = appid;
    }

    public void setSecret(String secret) {
        WechatConfig.secret = secret;
    }

    public static String getAppid() {
        return appid;
    }

    public static String getSecret() {
        return secret;
    }
}
