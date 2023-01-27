package team.delete.pursebuddy.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.delete.pursebuddy.config.WechatConfig;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.dto.Code2SessionDto;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.util.IntegerUtil;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
public class WeChatService {

    public Code2SessionDto code2Session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + WechatConfig.getAppid() +
                "&secret=" + WechatConfig.getSecret() +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Code2SessionDto code2Session = new Code2SessionDto()
                .setOpenId(jsonObject.getString("openid"))
                .setSessionKey(jsonObject.getString("session_key"))
                .setUnionId(jsonObject.getString("unionid"))
                .setErrCode(jsonObject.getInteger("errcode"))
                .setErrMsg(jsonObject.getString("errmsg"));
        if (!StringUtils.hasLength(code2Session.getOpenId()) ) {
            throw new AppException(ErrorCode.OPEN_ID_ERROR);
        }
        return code2Session;
    }
}
