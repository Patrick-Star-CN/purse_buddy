package team.delete.pursebuddy.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * @author patrick_star
 * @version 1.0
 */
@Data
@Builder
public final class ErrorDetail {
    private Integer code;
    private String message;
    private final String requestId;
    private final String path;
    private final Instant timestamp;
    private final Object data;


    public ErrorDetail(Integer code, String message, String requestId, String path, Instant timestamp, Object data) {
        this.code = code;
        this.message = message;
        this.requestId = requestId;
        this.path = path;
        this.timestamp = timestamp;
        this.data = data;
    }
}
