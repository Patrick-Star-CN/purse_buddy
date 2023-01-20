package team.delete.pursebuddy.constant;

import lombok.Getter;

/**
 * @author patrick_star
 * @Desc 错误类型枚举类
 * @version 1.0
 */
@Getter
public enum ErrorCode {

    /**
     * 非法请求
     */
    ILLEGAL_REQUEST(200404, "非法请求"),

    INVALID_CREDENTIAL(200100, "身份认证失败"),
    ;




    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
