package team.delete.pursebuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class TextInResponse {
    private int code;
    private String message;
    private Object result;
}
