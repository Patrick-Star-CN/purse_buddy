package team.delete.pursebuddy.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.dto.AjaxResult;
import team.delete.pursebuddy.entity.User;
import team.delete.pursebuddy.entity.UserInfo;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.service.UserInfoService;
import team.delete.pursebuddy.service.UserService;
import team.delete.pursebuddy.service.WeChatService;

import java.util.Map;

/**
 * @author Patrick_Star
 * @version 1.1
 */
@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    final UserInfoService userInfoService;

    final WeChatService weChatService;

    /**
     * 微信登录接口
     *
     * @param code 参数形式传入的用户code
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/login/{code}")
    public Object loginByWechat(@PathVariable String code) {
        String openId = weChatService.code2Session(code).getOpenId();
        User user = userService.getByOpenId(openId);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        StpUtil.logout(user.getUserId());
        StpUtil.login(user.getUserId());
        UserInfo userInfo = userInfoService.getById(user.getUserId());
        return AjaxResult.SUCCESS(Map.of("username", user.getUsername(),
                "gender", userInfo.getGender(),
                "birthday", userInfo.getBirthday()));
    }

    /**
     * 微信注册接口
     *
     * @param code     参数形式传入的微信小程序code
     * @param username 参数形式传入的用户名
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/register")
    public Object registerByWechat(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "gender") String gender,
            @RequestParam(value = "birthday") String birthday) {
        String openId = weChatService.code2Session(code).getOpenId();
        int userId = userService.insert(username, openId, gender, birthday);
        StpUtil.login(userId);
        return AjaxResult.SUCCESS();
    }

    /**
     * 测试登录接口
     *
     * @param uid 参数形式传入的用户id
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/test/login/{uid}")
    public Object login(@PathVariable Integer uid) {
        User user = userService.getById(uid);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        StpUtil.logout(uid);
        StpUtil.login(uid);
        UserInfo userInfo = userInfoService.getById(uid);
        return AjaxResult.SUCCESS(Map.of("username", user.getUsername(),
                "gender", userInfo.getGender(),
                "birthday", userInfo.getBirthday()));
    }

    /**
     * 用户修改性别接口
     *
     * @param gender 性别
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping("/change/gender")
    public Object modifyStudentGender(@RequestParam String gender) {
        int userId = StpUtil.getLoginIdAsInt();
        userInfoService.updateInfo(userId, gender, null);
        return AjaxResult.SUCCESS();
    }

    /**
     * 用户修改生日接口
     *
     * @param birthday 生日
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping("/change/birthday")
    public Object modifyStudentBirthday(@RequestParam String birthday) {
        int userId = StpUtil.getLoginIdAsInt();
        userInfoService.updateInfo(userId, null, birthday);
        return AjaxResult.SUCCESS();
    }

}
