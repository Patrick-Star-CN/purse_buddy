package team.delete.pursebuddy.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class Ledger {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Boolean isPublic;
    private Integer ownerId;
}
