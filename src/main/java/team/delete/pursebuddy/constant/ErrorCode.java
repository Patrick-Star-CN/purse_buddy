package team.delete.pursebuddy.constant;

import lombok.Getter;

/**
 * @author patrick_star
 * @Desc 错误类型枚举类
 * @version 1.0
 */
@Getter
public enum ErrorCode {

    ILLEGAL_REQUEST(250, "非法请求"),
    INVALID_CREDENTIAL(1000, "身份认证失败"),
    NOT_PERMITTED(1001, "无此权限"),
    ACCOUNT_BLOCKED(1002, "账号被封禁"),
    INVALID_VERIFYING_CODE(2001, "验证码错误"),
    EXPIRED_VERIFYING_CODE(2002, "验证码过期"),
    UNMATCHED_VERIFYING_CODE(2003, "验证码错误"),
    GENERATE_FAILED(2004, "验证码生成失败"),
    MESSAGE_ALREADY_SENT(3001, "短信验证码已发送，请稍后再试"),
    MESSAGE_FAILED_TO_SEND(3002, "短信验证码发送失败"),
    MESSAGE_NOT_SENT(3003, "未发送短信验证码"),
    INVALID_MESSAGE_CODE(3004, "短信验证码错误"),
    PHONE_NUMBER_NONE_EXIST(3006, "手机号未绑定或账号不存在"),
    PHONE_NUMBER_USED(3007, "手机号已被注册"),
    USERNAME_USED(3007, "用户名已被注册"),
    INVALID_FORMAT(4000, "格式校验错误"),
    STUDENT_NUMBER_REGISTERED(4001, "学号已被注册"),
    FAIL_TO_SUBSCRIBE(9001, "订阅失败"),

    OPERATION_NOT_AUTHORIZED(5001,"该用户没有此操作权限!"),
    PROJECT_NOT_EXIST(5002,"项目不存在！"),
    USER_NOT_EXIST(5003,"该用户不存在！"),
    RESOURCE_NOT_EXIST(5004,"项目资源不存在！"),
    TEAM_NUM_LIMIT(5005,"团队成员数达到上限!"),
    TEAM_MEMBER_ALREADY_EXIST(5006,"该队员已经存在！"),
    OPERATION_FORBIDDEN_DELETE_LEADER(5007,"操作违规，不可以删除队长！"),
    RANK_ALREADY_EXIST(5008,"此排名已存在人员，请先行修改！"),
    TEAM_LEADER_ALREADY_EXIST(5009,"队长无需加入排位，默认排位1！"),
    ILLEGAL_PROJECT_ACADEMY_STATUS(5010,"项目状态-院级管理员视角异常！"),
    PROJECT_HAS_BEEN_SUBMIT(5011,"该项目已被提交，无法修改！"),
    ACADEMY_REC_AMT_EXCEED(5012,"院级管理员推荐名额超出限制！"),
    ACADEMY_HAS_NO_PROJECT_TO_SUBMIT(5013,"无项目可以提交给校级管理员审核！"),
    ACADEMY_REC_TYPE_ERROR_CODE(5014,"普通推荐不可以推荐校团委直推项目和省级直推项目！省级推荐不可以推荐普通项目！"),
    COMPETITION_NOT_EXISTS(5015,"该竞赛不存在！"),
    ACADEMY_RETURN_FOR_MODIFY_NOT_QUALIFY(5016,"退回修改条件不满足，请检查项目不是校团委推荐 + 项目未拟推荐 + 项目不是自评三等奖！"),
    PROJECT_APP_JUDGE_LIMIT(5017, "本项目已经申请评委评论披露，请耐心等待院级管理员审核！"),
    PROJECT_APP_NOT_ALLOW(5018, "本项目申请评委评审披露未通过院级管理员审核，请撤回重新申请！"),
    SCHOOL_RETURN_FOR_MODIFY_NOT_QUALIFY(5019,"退回修改条件不满足，请检查项目是否已经被评委评审！"),

    STAGE_STATUS_TIME(6001,"时间校验错误！前一阶段结束时间和后一阶段开始时间应当共用！"),
    TIME_CHECK_FAILED(6002,"现在不能进行此操作！"),
    ILLEGAL_PROJECT_STU_STATUS(6003,"项目状态字段异常！"),
    ILLEGAL_COMPETITION_STATUS(6004,"竞赛状态字段异常！"),
    ILLEGAL_COMPETITION_NAME(6005,"竞赛名称字段异常！"),
    ILLEGAL_SUBMIT_OPERATION(6006,"当前提交操作不合法！"),
    OPERATION_FORBIDDEN_STAGE_STATUS(6007,"当前竞赛阶段状态不可以执行此操作！"),
    ANNOTATION_PARAM_ERROR(6008, "校验注解参数有误！"),
    FIND_STAGE_STATUS_CURRENT_TIME_FAIL(6009,"根据当前时间获取竞赛阶段失败！"),
    STAGE_TIME(6010,"时间校验错误！初赛公示结束时间应当小于复赛提交开始时间！"),
    ILLEGAL_PROJECT_APP_STATUS(6011, "申请评委评论披露状态异常！"),

    ILLEGAL_MEMBER_MIN_RANK(7003,"团队成员最小排位不得小于2！"),
    ILLEGAL_MEMBER_MAX_RANK(7004,"团队成员最大排位超上限！"),

    ONE_COMP_TYPE_IN_A_PERIOD(8001,"同一时间段同一个比赛类型只能有一个比赛正在进行！"),
    ILLEGAL_UPDATE_COMPELETED_COMPETITION(8002,"不可以修改已完结比赛"),
    ILLEGAL_COMPETITION_TH(8003,"比赛届数不可以小于已存在最大届数！"),
    COMPETITION_STAGE_FOR_ENTER_NEXT_STAGE_ILLEGAL(8004, "进入下一个阶段的阶段ID非法！"),

    SCHOOL_ACADEMY_MODIFY_QUOTA_INVALID(9001,"修改指标只能增大指标！"),
    SCHOOL_ACADEMY_MODIFY_QUOTA_FUNCTION_NOT_FOUND(9002,"修改指标函数名获取错误！"),

    JUDGE_ALREADY_COMMENT(10001,"该评委已评分，请修改或删除后再评分！"),
    JUDGE_SCORE_RANK_NOT_NULL(10002,"评委分数和排名不能都是空！"),
    JUDGE_ALREADY_ARRANGED_BY_THE_TAG(10003,"评委已经被指派过此竞赛的此组别！"),
    JUDGE_NOT_COMMENT_TO_MODIFY(10004, "评委还未评分，不可以修改！"),
    JUDGE_CANNOT_JUDGE_PROVINCE_REC_PROJECT(10005, "省级直推项目评委无需操作！"),


    ACADEMY_COMPETITION_LACK(11001,"无此学院竞赛信息！"),
    COMPETITION_KEY_POINT_LACK(11002,"无此竞赛关键点信息！"),
    COMPETITION_LACK(11003,"无此竞赛信息！"),
    COMPETITION_STAGE_AWARD_LACK(11004,"无此竞赛阶段奖励信息！"),
    COMPETITION_STAGE_LACK(11004,"无此竞赛阶段信息！"),
    COMPETITION_STAGE_STATUS_LACK(11004,"无此竞赛阶段状态信息！"),
    COMPETITION_STATIC_TAGS_LACK(11004,"无此竞赛阶段状态信息！"),
    PROJECT_MEMBER_LACK(11005,"无此项目成员信息！"),
    PROJECT_TEACHER_LACK(11006,"无此项目指导老师信息！"),
    STUDENT_INFO_LACK(11007,"无此学生信息！"),
    STAGE_NOT_EXIT(11008,"不存在这个竞赛阶段！"),
    NOT_SAME_COMPETITION(11009, "不是同一届比赛！"),
    PROJECT_NOT_PERMIT_TO_ENTER_NEXT_STAGE(11010, "项目未被允许进入下一个阶段！"),
    COMPETITION_STAGE_SUPPORT_MAX_TWO(11011, "目前比赛流程最多支持初赛和复赛两个阶段！")
    ;




    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
