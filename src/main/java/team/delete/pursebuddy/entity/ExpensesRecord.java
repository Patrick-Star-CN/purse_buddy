package team.delete.pursebuddy.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * @author Patrick_Star
 * @version 1.2
 */
@Data
@Builder
public class ExpensesRecord implements Comparable<ExpensesRecord> {
    /**
     * 消费记录id
     */
    @TableId(type = IdType.AUTO)
    Integer id;

    /**
     * 用户id
     */
    Integer userId;
    /**
     * 账本id
     */
    Integer ledgerId;
    /**
     * 消费金额
     */
    Double value;
    /**
     * 收支类型(0: 收入; 1: 支出）
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    Boolean type;
    /**
     * 消费种类
     */
    String kind;
    /**
     * 备注
     */
    String remark;
    /**
     * 消费日期
     */
    Date date;

    @Override
    public int compareTo(@NotNull ExpensesRecord rhs) {
        if (date.after(rhs.date)) {
            return -1;
        } else if (date.before(rhs.date)) {
            return 1;
        } else {
            return 0;
        }
    }
}
