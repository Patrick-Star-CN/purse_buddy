package team.delete.pursebuddy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Patrick_Star
 * @version 1.1
 */
public class IntegerUtil {
    private static Pattern pattern = Pattern.compile("-?[0-9]+(\\\\.[0-9]+)?");

    public static boolean equal(Integer left, Integer right) {
        if (right != null && left != null) {
            return right.intValue() == left.intValue();
        } else {
            return false;
        }
    }

    /**
     * 通过正则表达式判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        // 通过Matcher进行字符串匹配
        Matcher m = pattern.matcher(str);
        // 如果正则匹配通过 m.matches() 方法返回 true ，反之 false
        return m.matches();
    }

}
