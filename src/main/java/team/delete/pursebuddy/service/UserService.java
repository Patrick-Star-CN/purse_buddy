package team.delete.pursebuddy.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.entity.User;
import team.delete.pursebuddy.entity.UserInfo;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.mapper.UserInfoMapper;
import team.delete.pursebuddy.mapper.UserMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ExpireOneMin")
public class UserService {
    final UserMapper userMapper;

    final UserInfoMapper userInfoMapper;

    /**
     * 通过 ID 查询用户
     */
    @Cacheable(key = "'user_'+#userId")
    public User getById(Integer userId) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
    }

    /**
     * 通过 openId 查询用户
     */
    @Cacheable(key = "'user_'+#openId")
    public User getByOpenId(String openId) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("open_id", openId));
    }

    /**
     * 通过 openId 查询用户是否存在
     */
    @Cacheable(key = "'not_exist_'+#openId")
    public boolean exist(String openId) {
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("open_id", openId));
        return count != 0;
    }

    /**
     * 获取当前登陆用户
     */
    public User getLoginUser() {
        int userId = StpUtil.getLoginIdAsInt();
        return getById(userId);
    }

    /**
     * 注册用户
     */
    @Transactional(rollbackFor = Exception.class)
    public int insert(String username, String openId) {
        if (exist(openId)) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = User.builder().username(username)
                .openId(openId).build();
        if (userMapper.insert(user) != 1) {
            throw new AppException(ErrorCode.SERVER_ERROR);
        }
        int userId = user.getUserId();
        UserInfo userInfo = UserInfo.builder().userId(userId).build();
        if (userInfoMapper.insert(userInfo) != 1) {
            throw new AppException(ErrorCode.SERVER_ERROR);
        }
        return userId;
    }
}
