package com.lzpeng.project.sys.config;

import com.lzpeng.project.sys.domain.*;
import com.lzpeng.project.sys.service.MenuService;
import com.lzpeng.project.sys.service.RoleService;
import com.lzpeng.project.sys.service.UserService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * 初始化系统管理模块
 *
 * @date: 2020/2/23
 * @time: 16:37
 * @author: 李志鹏
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
public class SysInitialize implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void run(ApplicationArguments args) throws IOException {

        if (SysStatic.needInit) {
            SysStatic.needInit = false;
            User administratorUser = userService.findByUsername("administrator");
            User userUser = userService.findByUsername("user");
            User anonymousUser = userService.findByUsername("anonymous");
            Role adminRole = jpaQueryFactory.selectFrom(QRole.role).where(QRole.role.number.eq("ADMIN")).fetchOne();
            Role userRole = jpaQueryFactory.selectFrom(QRole.role).where(QRole.role.number.eq("USER")).fetchOne();
            Role activitiAdminRole = jpaQueryFactory.selectFrom(QRole.role).where(QRole.role.number.eq("ACTIVITI_ADMIN")).fetchOne();
            Role activitiUserRole = jpaQueryFactory.selectFrom(QRole.role).where(QRole.role.number.eq("ACTIVITI_USER")).fetchOne();
            Role anonymousRole = jpaQueryFactory.selectFrom(QRole.role).where(QRole.role.number.eq("ANONYMOUS")).fetchOne();
            userService.setRoles(administratorUser.getId(), Stream.of(adminRole, activitiAdminRole, activitiUserRole).map(Role::getId).toArray(String[]::new));
            userService.setRoles(userUser.getId(), Stream.of(userRole, activitiUserRole).map(Role::getId).toArray(String[]::new));
            userService.setRoles(anonymousUser.getId(), Stream.of(anonymousRole).map(Role::getId).toArray(String[]::new));
            log.info("初始化用户角色关联信息成功");
            List<Menu> adminMenus = menuService.findAll();
            adminRole.setMenus(adminMenus);
            roleService.save(adminRole);
            List<Menu> userMenus = jpaQueryFactory.selectFrom(QMenu.menu).where(QMenu.menu.number.endsWith("query").or(QMenu.menu.number.endsWith("export"))).fetch();
            roleService.setPermissions(userRole.getId(), userMenus.stream().map(Menu::getId).toArray(String[]::new));
            log.info("初始化角色菜单关联信息成功");
        }
    }
}
