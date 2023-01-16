package team.delete.pursebuddy.constant;

import lombok.Getter;

/**
 * @author patrick_star
 * @version 1.0
 */
@Getter
public enum ResponseCode {
    SUCCESS(200),
    FAIL(400),
    UN_AUTH(401),
    FORBIDDEN(403);

    private final Integer code;

    ResponseCode(Integer code) {
        this.code = code;
    }
}
