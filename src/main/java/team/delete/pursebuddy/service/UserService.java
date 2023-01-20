package team.delete.pursebuddy.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team.delete.pursebuddy.entity.User;
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

    /**
     * 通过 ID 查询用户
     */
    @Cacheable(key = "'user_'+#userId")
    public User getUserById(Integer userId) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
    }

    /**
     * 通过 openId 查询用户
     */
    @Cacheable(key = "'user_'+#openId")
    public User getUserByOpenId(String openId) {
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
        return getUserById(userId);
    }
}
