package com.lzpeng.project.sys.config;

import cn.hutool.core.io.IoUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.framework.domain.SimpleSpecificationBuilder;
import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.project.sys.domain.User;
import com.lzpeng.project.sys.service.MenuService;
import com.lzpeng.project.sys.service.RoleService;
import com.lzpeng.project.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.lzpeng.framework.domain.SpecificationOperator.Op.END;
import static com.lzpeng.framework.domain.SpecificationOperator.Op.EQUALS;

/**
 * 初始化系统管理模块
 * @date: 2020/2/23
 * @time: 16:37
 * @author: 李志鹏
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SysInitialize implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Resource userData = new ClassPathResource("static/user.json");

    private Resource menuData = new ClassPathResource("static/menu.json");

    private Resource roleData = new ClassPathResource("static/role.json");


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void run(ApplicationArguments args) throws IOException {
        QueryResult<User> result = userService.query(1, 1, null);
        if (result.isEmpty()) {
            roleService.importDataFromJson(IoUtil.read(roleData.getInputStream(), Charset.defaultCharset()));
            log.info("初始化角色信息成功");
            userService.importDataFromJson(IoUtil.read(userData.getInputStream(), Charset.defaultCharset()));
            log.info("初始化用户信息成功");
            User administratorUser = userService.findByUsername("administrator");
            User userUser = userService.findByUsername("user");
            User anonymousUser = userService.findByUsername("anonymous");
            Role adminRole = roleService.findOne(
                    new SimpleSpecificationBuilder("number", EQUALS, "ADMIN")
                            .build());
            Role userRole = roleService.findOne(
                    new SimpleSpecificationBuilder("number", EQUALS, "USER")
                            .build());
            Role activitiAdminRole = roleService.findOne(
                    new SimpleSpecificationBuilder("number", EQUALS, "ACTIVITI_ADMIN")
                            .build());
            Role activitiUserRole = roleService.findOne(
                    new SimpleSpecificationBuilder("number", EQUALS, "ACTIVITI_USER")
                            .build());
            Role anonymousRole = roleService.findOne(
                    new SimpleSpecificationBuilder("number", EQUALS, "ANONYMOUS")
                            .build());
            userService.setRoles(administratorUser.getId(), Stream.of(adminRole, activitiAdminRole, activitiUserRole).map(Role::getId).toArray(String[]::new));
            userService.setRoles(userUser.getId(), Stream.of(userRole,  activitiUserRole).map(Role::getId).toArray(String[]::new));
            userService.setRoles(anonymousUser.getId(), Stream.of(anonymousRole).map(Role::getId).toArray(String[]::new));
            log.info("初始化用户角色关联信息成功");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("anonymous", "123456"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            menuService.importDataFromJson(IoUtil.read(menuData.getInputStream(), Charset.defaultCharset()));
            log.info("初始化菜单信息成功");
            Collection<Menu> adminMenus = menuService.findAll();
            adminRole.setMenus(adminMenus);
            roleService.save(adminRole);
            List<Menu> userMenus = menuService.findAll(
                    new SimpleSpecificationBuilder("number", END, "query")
                            .or("number", END, "export")
                            .build());
            roleService.setPermissions(userRole.getId(), userMenus.stream().map(Menu::getId).toArray(String[]::new));
            log.info("初始化角色菜单关联信息成功");
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
