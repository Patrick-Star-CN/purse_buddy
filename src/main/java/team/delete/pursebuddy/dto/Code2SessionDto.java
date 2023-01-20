package team.delete.pursebuddy.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class Code2SessionDto {

    private String openId;

    private String sessionKey;

    private String unionId;

    private Integer errCode;

    private String errMsg;
}
