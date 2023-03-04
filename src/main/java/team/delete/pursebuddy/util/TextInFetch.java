package team.delete.pursebuddy.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import team.delete.pursebuddy.config.XTiConfig;
import team.delete.pursebuddy.dto.TextInResponse;

/**
 * 调用合合科技 OCR 接口识别图片的工具
 * @author Patrick_Star
 * @version 1.0
 */
public class TextInFetch {

    private static RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static ResponseEntity<TextInResponse> post(String url, byte[] img) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-ti-app-id", XTiConfig.getAppid());
        headers.add("x-ti-secret-code", XTiConfig.getSecret());
        headers.add("connection", "Keep-Alive");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(img, headers);
        return getRestTemplate().postForEntity(url, httpEntity, TextInResponse.class);
    }
}
