package team.delete.pursebuddy.constant;

import org.springframework.stereotype.Component;

/**
 * @author patrick_star
 * @version 1.0
 */
@Component
public class MsgLocalConstant {
    public static final String USER_MESSAGES = "u:msg:%d";
    public static final String USER_MSG_QUEUE = "u:{%d}:msg:q";
    public static final String USER_MSG_SENT = "u:{%d}:msg:st";
    public static final String USER_MSG_UNREAD = "u:{%d}:msg:ur";

    public String getKeyUserMsgQueue(int userId) {
        return String.format(USER_MSG_QUEUE, userId);
    }

    public String getKeyUserMsgSent(int userId) {
        return String.format(USER_MSG_SENT, userId);
    }

    public String getKeyUserMsgUnread(int userId) {
        return String.format(USER_MSG_UNREAD, userId);
    }
}
