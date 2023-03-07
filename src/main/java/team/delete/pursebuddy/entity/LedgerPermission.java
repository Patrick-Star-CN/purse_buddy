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
public class LedgerPermission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer ledgerId;
}
