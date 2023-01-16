package team.delete.pursebuddy.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author patrick_star
 * @Desc 缓存枚举类
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum CacheType {
    NoExpire(100, 3000, false,-1),
    ExpireOneMin(100, 1000, true, 60),
    Captcha(100, 3000, true, 60),
    SMS(100, 3000, true, 300),
    Roles(100, 3000, false, -1),
    Message(1000, 10000, false, -1),
    Permissions(100, 3000, false, -1);

    private final int initialCapacity;
    private final int maximumSize;
    private final boolean expires;
    private final int expireTime;
}
