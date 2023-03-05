package team.delete.pursebuddy.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import team.delete.pursebuddy.config.WechatConfig;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.exception.AppException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Patrick_Star
 * @version 1.0
 */
public class VoiceService {
    private static RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static String getAccessToken() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        if (Boolean.TRUE.equals(redisTemplate.hasKey("access_token"))) {
            return redisTemplate.opsForValue().get("access_token");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> params = new HashMap<>(3);
        params.put("grant_type", "client_credential");
        params.put("appid", WechatConfig.getAppid());
        params.put("secret", WechatConfig.getSecret());
        Map<String, Object> resp = (Map<String, Object>) getRestTemplate().getForObject(url, Map.class, params);
        assert resp != null;
        if (resp.get("errcode") != null) {
            System.out.println(resp.get("errmsg"));
            throw new AppException(ErrorCode.ACCESS_TOKEN_ERROR);
        }
        redisTemplate.opsForValue().set("access_token", (String) resp.get("access_token"), (int) resp.get("expires_in"), TimeUnit.SECONDS);
        return (String) resp.get("access_token");
    }

    public static String insertByVoice(int userId, byte[] voice) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        if (Boolean.TRUE.equals(redisTemplate.hasKey("access_token"))) {
            return redisTemplate.opsForValue().get("access_token");
        }
        String url = "http://api.weixin.qq.com/cgi-bin/media/voice/addvoicetorecofortext";
        Map<String, String> params = new HashMap<>(3);
        params.put("grant_type", "client_credential");
        params.put("appid", WechatConfig.getAppid());
        params.put("secret", WechatConfig.getSecret());
        Map<String, Object> resp = (Map<String, Object>) getRestTemplate().getForObject(url, Map.class, params);
        assert resp != null;
        if (resp.get("errcode") != null) {
            System.out.println(resp.get("errmsg"));
            throw new AppException(ErrorCode.ACCESS_TOKEN_ERROR);
        }
        redisTemplate.opsForValue().set("access_token", (String) resp.get("access_token"), (int) resp.get("expires_in"), TimeUnit.SECONDS);
        return (String) resp.get("access_token");
    }
}
