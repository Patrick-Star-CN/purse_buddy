package team.delete.pursebuddy.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import team.delete.pursebuddy.config.XTiConfig;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.dto.TextInResponse;
import team.delete.pursebuddy.exception.AppException;

/**
 * 调用合合科技 OCR 接口识别图片的工具
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class TextInFetch {

    private static RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static Object post(String url, byte[] img) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-ti-app-id", XTiConfig.getAppid());
        headers.add("x-ti-secret-code", XTiConfig.getSecret());
        headers.add("connection", "Keep-Alive");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(img, headers);
        ResponseEntity<TextInResponse> resp = getRestTemplate().postForEntity(url, httpEntity, TextInResponse.class);
        if (resp.getBody() == null) {
            throw new AppException(ErrorCode.SERVER_ERROR);
        } else if (resp.getBody().getCode() != 200) {
            switch (resp.getBody().getCode()) {
                case 40301: {
                    throw new AppException(ErrorCode.IMAGE_TYPE_ERROR);
                }
                case 40302: {
                    throw new AppException(ErrorCode.IMAGE_SIZE_ERROR);
                }
                case 40303: {
                    throw new AppException(ErrorCode.FILE_TYPE_ERROR);
                }
                case 40304: {
                    throw new AppException(ErrorCode.IMAGE_MEASUREMENT_ERROR);
                }
                default: {
                    System.out.println(resp.getBody().getMessage());
                    throw new AppException(ErrorCode.SERVER_ERROR);
                }
            }
        }
        return resp.getBody().getResult();
    }
}
