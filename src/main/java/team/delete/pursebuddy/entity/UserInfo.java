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
public class UserInfo {
    /**
     * 用户id
     */
    @TableId(type = IdType.NONE)
    int userId;
    /**
     * 性别
     */
    String gender;
    /**
     * 生日
     */
    String birthday;
}
