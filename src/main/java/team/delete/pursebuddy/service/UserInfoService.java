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
import team.delete.pursebuddy.entity.User;
import team.delete.pursebuddy.entity.UserInfo;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.mapper.UserInfoMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Patrick_Star
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ExpireOneMin")
public class UserInfoService {

    final UserInfoMapper userInfoMapper;

    /**
     * 通过 ID 查询用户信息
     */
    @Cacheable(key = "'user_info_'+#userId")
    public UserInfo getById(int userId) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userId));
    }

    @CacheEvict(value = "NoExpire", key = "'user_info_'+#userId")
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(int userId, String gender, String birthday) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdayDt;
        try {
            birthdayDt = birthday == null ? null : simpleDateFormat.parse(birthday);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (gender != null && !"男".equals(gender) && !"女".equals(gender)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId)
                .set(gender != null, "gender", gender)
                .set(birthdayDt != null, "birthday", birthdayDt);
        userInfoMapper.update(null, wrapper);
    }
}
