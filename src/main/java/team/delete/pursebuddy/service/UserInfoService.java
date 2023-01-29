package team.delete.pursebuddy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.constant.RegexPattern;
import team.delete.pursebuddy.entity.UserInfo;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.mapper.UserInfoMapper;

import java.util.regex.Pattern;

/**
 * @author Patrick_Star
 * @version 1.2
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ExpireOneMin")
public class UserInfoService {

    final UserInfoMapper userInfoMapper;

    /**
     * 通过 ID 查询用户信息
     */
    @Cacheable(value = "ExpireOneMin", key = "'user_info_'+#userId")
    public UserInfo getById(int userId) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userId));
    }

    /**
     * 更新用户信息
     */
    @CacheEvict(value = "NoExpire", key = "'user_info_'+#userId")
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(int userId, String gender, String birthday) {
        if (birthday != null && !Pattern.matches(RegexPattern.DATE, birthday)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (gender != null && !"男".equals(gender) && !"女".equals(gender)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId)
                .set(gender != null, "gender", gender)
                .set(birthday != null, "birthday", birthday);
        userInfoMapper.update(null, wrapper);
    }
}
