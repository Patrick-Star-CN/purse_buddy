package team.delete.pursebuddy.exception.app;

import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.exception.AppException;

/**
 * @author patrick_star
 * @version 1.0
 */
public class InvalidCredentialException extends AppException {

    public InvalidCredentialException() {
        super(ErrorCode.INVALID_CREDENTIAL, null);
    }
}
