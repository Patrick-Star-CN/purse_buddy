package team.delete.pursebuddy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class Account {
    /**
     * 账户id
     */
    @TableId(type = IdType.AUTO)
    Integer id;
    /**
     * 用户id
     */
    Integer userId;
    /**
     * 账户名称
     */
    String name;
}
