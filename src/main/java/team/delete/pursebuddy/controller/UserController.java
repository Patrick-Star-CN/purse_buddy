package team.delete.pursebuddy.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.delete.pursebuddy.dto.AjaxResult;
import team.delete.pursebuddy.entity.User;
import team.delete.pursebuddy.entity.UserInfo;
import team.delete.pursebuddy.exception.app.InvalidCredentialException;
import team.delete.pursebuddy.service.UserInfoService;
import team.delete.pursebuddy.service.UserService;
import team.delete.pursebuddy.service.WeChatService;

import java.util.Map;

/**
 * @author Patrick_Star
 * @version 1.0
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
     * @param code 参数形式传入的用户code
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/wechat/login")
    public Object loginByWechat(@RequestParam(value = "code") String code,
                                HttpServletRequest request) {
        HttpSession session = request.getSession();
        String openId = weChatService.code2Session(code).getOpenId();
        User user = userService.getUserByOpenId(openId);
        if (user == null) {
            throw new InvalidCredentialException();
        }
        StpUtil.logout(user.getUserId());
        StpUtil.login(user.getUserId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        UserInfo userInfo = userInfoService.getUserById(user.getUserId());
        session.setAttribute("id", String.valueOf(tokenInfo));
        return AjaxResult.SUCCESS(Map.of("username", user.getUsername(),
                "gender", userInfo.getGender(),
                "birthday", userInfo.getBirthday()));
    }
}
