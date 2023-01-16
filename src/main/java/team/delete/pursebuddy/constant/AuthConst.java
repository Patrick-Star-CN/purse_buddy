/*
 * Project: aha_zjut
 *
 * File Created at 2021/8/8
 *
 * 自定义时间格式代替DATE标签：2021-08-08 15:12:3:12 下午
 */

package team.delete.pursebuddy.constant;


/**
 * @author patrick_star
 * @version 1.0
 */
public class AuthConst {
    /**
     *  私有构造方法
     */
    private AuthConst() {
    }


    // --------------- 代表身份的权限 ---------------

    public static final String R_student = "student"; 			 // 角色_id_学生 超级管理员
    public static final String R_judge   = "judge"; 	    	 // 角色_id 评委
    public static final String R_academy = "admin_academy"; 	    	 // 角色_id 院级管理员
    public static final String R_school = "admin_school"; 	    	 // 角色_id 校级管理员
    public static final String R_supper = "super_admin";		         // 角色_id 超级管理员 进入后台权限，没有此权限无法进入后台管理


    // --------------- 所有权限码 ---------------

    public static final String AUTH = "auth";		                        // 权限管理
    public static final String USER_ROLE_LIST = "user-role-list";		    // 权限管理 - 用户角色管理
    public static final String ROLE_PERMISSION_LIST = "role-permission-list";		    // 权限管理 - 角色权限码管理
    public static final String MENU_LIST = "menu-list";		                // 权限管理 - 菜单列表
    public static final String ADMIN_LIST = "admin-list";	                // 权限管理 - 管理员列表
    public static final String ADMIN_ADD = "admin-add";		                // 权限管理 - 管理员添加
    public static final String COMP_TAGS_LIST = "comp-tags-list";           // 权限管理 - 赛事标签固定表（固定的几个分组)增删改权限

    // ----------------项目关联的额权限码------------
    public static final String MODIFY_PROJECT = "modify_project";		   // 修改项目

}
