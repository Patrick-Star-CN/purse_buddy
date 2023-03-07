package team.delete.pursebuddy.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.constant.RegexPattern;
import team.delete.pursebuddy.entity.User;
import team.delete.pursebuddy.entity.UserInfo;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.mapper.UserInfoMapper;
import team.delete.pursebuddy.mapper.UserMapper;

import java.util.regex.Pattern;

/**
 * @author Patrick_Star
 * @version 1.3
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackForClassName="RuntimeException")
@CacheConfig(cacheNames = "ExpireOneMin")
public class UserService {
    final UserMapper userMapper;

    final UserInfoMapper userInfoMapper;

    final LedgerService ledgerService;

    /**
     * 通过 ID 查询用户
     */
    @Cacheable(value = "ExpireOneMin", key = "'user_'+#userId")
    public User getById(Integer userId) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
    }

    /**
     * 通过 openId 查询用户
     */
    @Cacheable(value = "ExpireOneMin", key = "'user_'+#openId")
    public User getByOpenId(String openId) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("open_id", openId));
    }

    /**
     * 通过 openId 查询用户是否存在
     */
    @Cacheable(value = "ExpireOneMin", key = "'not_exist_'+#openId")
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
    public int insert(String username, String openId, String gender, String birthday) {
        if (exist(openId)) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (!Pattern.matches(RegexPattern.DATE, birthday)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (!"男".equals(gender) && !"女".equals(gender)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        User user = User.builder().username(username)
                .openId(openId).build();
        userMapper.insert(user);
        int userId = user.getUserId();
        UserInfo userInfo = UserInfo.builder().userId(userId)
                .gender(gender)
                .birthday(birthday).build();
        userInfoMapper.insert(userInfo);
        ledgerService.createDefault(user);
        return userId;
    }
}
