package team.delete.pursebuddy.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class ExpensesRecord {
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
     * 消费金额
     */
    Integer value;
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
     * 消费日期
     */
    Date date;
}
