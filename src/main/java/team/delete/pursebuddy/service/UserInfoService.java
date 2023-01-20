package team.delete.pursebuddy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team.delete.pursebuddy.entity.User;
import team.delete.pursebuddy.entity.UserInfo;
import team.delete.pursebuddy.mapper.UserInfoMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ExpireOneMin")
public class UserInfoService {

    final UserInfoMapper userInfoMapper;

    /**
     * 通过 ID 查询用户信息
     */
    @Cacheable(key = "'user_'+#userId")
    public UserInfo getUserById(Integer userId) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userId));
    }
}
