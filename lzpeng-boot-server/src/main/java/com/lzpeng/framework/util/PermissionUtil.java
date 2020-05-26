package com.lzpeng.framework.util;

import com.lzpeng.framework.web.config.UserAuditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @date: 2020/5/27
 * @time: 0:19
 * @author: 李志鹏
 */
@Component
public class PermissionUtil {

    private static UserAuditor userAuditor;

    @Autowired
    public void setUserAuditor(UserAuditor userAuditor) {
        PermissionUtil.userAuditor = userAuditor;
    }

    /**
     * 判断当前用户是否拥有权限
     * @param permission 权限
     * @return
     */
    public static boolean hasPermission(String permission){
        Collection<? extends String> authorities = userAuditor.getCurrentUserAuthorities();
        return authorities.contains(permission);
    }

    /**
     * 判断当前用户是否拥有角色
     * @param role 角色
     * @return
     */
    public static boolean hasRole(String role){
        Collection<? extends String> authorities = userAuditor.getCurrentUserAuthorities();
        return authorities.contains("ROLE_" + role);
    }
}
