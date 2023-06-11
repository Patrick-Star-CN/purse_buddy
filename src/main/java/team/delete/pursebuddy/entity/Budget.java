package team.delete.pursebuddy.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class Budget {
    private Integer id;
    private Integer userId;
    private Integer type;
    private Date time;
    private Double value;
}
