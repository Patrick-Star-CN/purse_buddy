package team.delete.pursebuddy.constant;

import org.springframework.stereotype.Component;

/**
 * @author patrick_star
 * @version 1.0
 */
@Component
public class MsgTypeRedisConstant {
    // 用户入队提示消息模板    您已被用户ID:113, 用户名Vc 拉入项目测试校团委推荐，排位2
    public static final String MSG_TYPE_IN_TEAM_TITLE = "项目队伍通知";
    public static final String MSG_TYPE_IN_TEAM_CONTENT = "您已被用户ID:{%d}, 用户名{%s} {%s}项目{%s}, 排位{%d}";

    public static String getKeyInTeam(int userId, String userName, String action, String projectName, int rank) {
        return String.format(MSG_TYPE_IN_TEAM_CONTENT, userId, userName, action, projectName, rank);
    }

    // 项目退回修改消息模板    您的项目测试校团委推荐被校级管理员退回，退回评论为：啥啊这是
    public static final String MSG_TYPE_PROJECT_RETURN_TITLE = "项目退回修改通知";
    public static final String MSG_TYPE_PROJECT_RETURN_CONTENT = "您的项目{%s}被{%s}退回，退回评论为：{%s}";

    public static String getKeyProjectReturn(String projectName, String roleName, String comment) {
        return String.format(MSG_TYPE_PROJECT_RETURN_CONTENT, projectName, roleName, comment);
    }

    // 查看评委信息申请        您的项目测试校团委推荐查看评委信息申请已经被院级管理员通过/拒绝
    public static final String MSG_TYPE_APP_JUDGE_TITLE = "申请评委评论披露通知";
    public static final String MSG_TYPE_APP_JUDGE_CONTENT = "您的项目{%s}查看评委信息申请已经被{%s}{%s}，以下是院级管理员的留言：{%s}";

    public static String getKeyAppJudge(String projectName, String roleName, String action, String comment) {
        return String.format(MSG_TYPE_APP_JUDGE_CONTENT, projectName, roleName, action, comment);
    }

    // 项目晋级通知            您的项目测试校团委推荐已经被批准晋级，请在竞赛下一阶段提交开始后进行项目复制！
    public static final String MSG_TYPE_ENTER_NEXT_STAGE_TITLE = "项目晋级通知";
    public static final String MSG_TYPE_ENTER_NEXT_STAGE_CONTENT = "您的项目{%s}已经被批准晋级，请在竞赛下一阶段提交开始后进行项目复制！";

    public static String getKeyEnterNextStage(String projectName) {
        return String.format(MSG_TYPE_ENTER_NEXT_STAGE_CONTENT, projectName);
    }

    // 普通消息
    public static final String MSG_SYS_NAME = "Aha-zjut系统管理员";
    public static final String USER_NORMAL_MSG_TITLE = "普通消息";
}
