package team.delete.pursebuddy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
public class ExpensesRecordDto {
    /**
     * 消费记录 id
     */
    Integer id;

    /**
     * 收支类型(0: 收入; 1: 支出）
     */
    @NotNull(message = "收支类型不能为空")
    Boolean type;

    /**
     * 消费金额
     */
    @NotNull(message = "消费金额不能为空")
    Integer value;

    /**
     * 消费种类
     */
    @NotNull(message = "消费种类不能为空")
    String kind;

    /**
     * 消费日期
     */
    @NotNull(message = "消费日期不能为空")
    String date;
}
