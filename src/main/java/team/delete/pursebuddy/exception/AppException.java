package team.delete.pursebuddy.exception;

import lombok.Getter;
import team.delete.pursebuddy.constant.ErrorCode;

/**
 * @author patrick_star
 * @Desc 自定义异常
 * @version 1.0
 */
@Getter
public class AppException extends RuntimeException{
    private final ErrorCode code;
    private final Object data;

    public AppException(ErrorCode code) {
        this.code = code;
        this.data = null;
    }

    public AppException(ErrorCode code, Object data) {
        this.code = code;
        this.data = data;
    }
}
