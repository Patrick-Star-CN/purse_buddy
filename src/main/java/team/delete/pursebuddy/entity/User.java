package team.delete.pursebuddy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.1
 */
@Data
@Builder
public class User {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    Integer userId;
    /**
     * 用户名
     */
    String username;
    /**
     * 微信小程序 openId
     */
    String openId;
}
