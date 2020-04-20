package com.lzpeng.project.sys.service;

import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.project.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 用户 业务层
 * @date: 2020/2/1
 * @time: 23:44
 * @author: 李志鹏
 */
@Service("userDetailsService")
@Transactional(rollbackOn = Exception.class)
public class UserService extends AbstractUserService implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    private Pattern BCRYPT_PATTERN = Pattern
            .compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public boolean beforeSave(User user) {
        String rawPassword = user.getPassword();
        if (StringUtils.isEmpty(rawPassword)) {
            rawPassword = "123456";
        }
        if (!BCRYPT_PATTERN.matcher(rawPassword).matches()) {
            String encodePassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodePassword);
        }
        return true;
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public User findById(String id) {
        return super.findById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);
        if (user == null) {
            user = loadUserByUsername("anonymous");
        }
        return user;
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 注册
     * @return
     */
    public User register(User user){
        return save(user);
    }

    /**
     * 分配角色
     * @param userId 用户id
     * @param roleIds 角色id数组
     * @return
     */
    public User setRoles(String userId, String[] roleIds) {
        User user = findById(userId);
        List<Role> roles = roleService.findAllById(Arrays.asList(roleIds));
        user.setRoles(roles);
        user = save(user);
        return user;
    }


//    /**
//     * 获取当前用户
//     * @return
//     */
//    public UserDetails getCurrentUser(){
//        return userAuditor.getCurrentUser();
//    }
//
//    /**
//     * 获取 authentication 中的用户
//     * @return
//     */
//    public UserDetails getCurrentUser(Authentication authentication){
//        return userAuditor.getCurrentUser(authentication);
//    }
//    /**
//     * 获取 principal 中的用户
//     * @return
//     */
//    public UserDetails getCurrentUserByPrincipal(Object principal){
//        return userAuditor.getCurrentUserByPrincipal(principal);
//    }

}
