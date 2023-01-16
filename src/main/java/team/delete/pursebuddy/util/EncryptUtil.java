package team.delete.pursebuddy.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author patrick_star
 * @Desc 加密解密工具类
 * @version 1.0
 */
public class EncryptUtil {
    /**
     * @Desc SHA256散列
     * @param sourceStr 目标字符串
     * @return 散列结果
     */
    public static String getSha256(String sourceStr) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        messageDigest.update(sourceStr.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(messageDigest.digest());
    }

    /**
     * @Desc 二进制数据转base64
     * @param binaryData 二进制数据
     * @return base64字符串
     */
    public static String toBase64String(byte[] binaryData) {
        return new String(Base64.encodeBase64(binaryData));
    }
}