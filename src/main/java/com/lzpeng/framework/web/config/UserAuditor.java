package com.lzpeng.framework.web.config;

import com.lzpeng.framework.util.ThreadLocalUtil;
import com.lzpeng.project.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @date: 2020/3/6
 * @time: 19:27
 * @author:   李志鹏
 */
//@Component("defaultUserAuditor")
//@ConditionalOnMissingBean(name = "userAuditor")
@Component
public class UserAuditor implements AuditorAware<String> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Optional<String> getCurrentAuditor() {
        UserDetails user =  getCurrentUser();
        if (user != null) {
            if (user instanceof User) {
                // 数据库中真实存在的用户
                return Optional.ofNullable(((User) user).getId());
            } else {
                // 单元测试模拟用户
                return Optional.ofNullable(user.getUsername());
            }
        }
        return Optional.empty();
    }

    /**
     * 使用 ThreadLocal 来维护各个线程的 用户
     * 不使用 ThreadLocal, 在操作菜单表每次从数据库获取用户时会抛 StackOverflowError
     * @see StackOverflowError
     */
    public UserDetails getCurrentUser() {
        if (ThreadLocalUtil.USER.get() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = getCurrentUser(authentication);
            ThreadLocalUtil.USER.set(user);
        }
        return ThreadLocalUtil.USER.get();
    }

    /**
     * 获取 authentication 中的用户
     *
     * @return
     */
    private UserDetails getCurrentUser(Authentication authentication) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            UserDetails user = getCurrentUserByPrincipal(principal);
            return user;
        }
        return null;
    }

    /**
     * 获取 principal 中的用户
     *
     * @return
     */
    private UserDetails getCurrentUserByPrincipal(Object principal) {
        if (principal != null) {
            if (principal instanceof UserDetails) {
                return (UserDetails) principal;
            }
            if (principal instanceof String) {
                String username = (String) principal;
                UserDetails user = userDetailsService.loadUserByUsername(username);
                return user;
            }
        }
        return null;
    }

}
