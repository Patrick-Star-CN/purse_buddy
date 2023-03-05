package team.delete.pursebuddy.dto;

import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.1
 */
@Data
public class TextInResponse {
    private int code;
    private String message;
    private Object result;
}
