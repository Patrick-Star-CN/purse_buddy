package team.delete.pursebuddy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.2
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
    String value;

    /**
     * 消费种类
     */
    @NotNull(message = "消费种类不能为空")
    String kind;

    /**
     * 备注
     */
    String remark;

    /**
     * 消费日期
     */
    @NotNull(message = "消费日期不能为空")
    String date;

    /**
     * 账本id
     */
    @NotNull(message = "账本id不能为空")
    Integer ledgerId;
}
