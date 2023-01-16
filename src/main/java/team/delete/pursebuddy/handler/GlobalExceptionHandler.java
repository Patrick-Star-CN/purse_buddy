package team.delete.pursebuddy.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import team.delete.pursebuddy.config.ProfileConfig;
import team.delete.pursebuddy.dto.AjaxResult;
import team.delete.pursebuddy.dto.ErrorDetail;

import java.time.Instant;

/**
 * @author patrick_star
 * @Desc 未被其余handler处理，则最终进入该handler处理，处理Exception子类
 * @version 1.0
 */
@ControllerAdvice
@Order(1000)
@RequiredArgsConstructor
public class GlobalExceptionHandler {


     final ProfileConfig profileConfig;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult<Object> handleGlobalException(Exception e, HttpServletRequest request) {
        if (profileConfig.isDev()) {
            // dev环境时返回的异常信息
            ErrorDetail errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(e.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
            e.printStackTrace();
            return AjaxResult.FAIL("全局异常", errorDetail);
        } else {
            // 其他环境时返回的异常信息
            ErrorDetail errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();

            return AjaxResult.FAIL("请求失败", errorDetail);
        }

    }

}
