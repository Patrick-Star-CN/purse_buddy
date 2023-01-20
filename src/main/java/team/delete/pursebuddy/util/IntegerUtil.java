package team.delete.pursebuddy.util;

/**
 * @author Patrick_Star
 * @version 1.0
 */
public class IntegerUtil {
    public static boolean equal(Integer left, Integer right) {
        if (right != null && left != null) {
            return right.intValue() == left.intValue();
        } else {
            return false;
        }
    }
}
