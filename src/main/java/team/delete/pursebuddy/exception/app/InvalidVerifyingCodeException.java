package team.delete.pursebuddy.exception.app;


import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.exception.AppException;

/**
 * @author patrick_star
 * @version 1.0
 */
public class InvalidVerifyingCodeException extends AppException {
    public InvalidVerifyingCodeException() {
        super(ErrorCode.INVALID_VERIFYING_CODE, null);
    }
}
