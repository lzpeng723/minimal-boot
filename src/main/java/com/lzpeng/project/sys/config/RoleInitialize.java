package com.lzpeng.project.sys.config;

import cn.hutool.core.io.IoUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.project.sys.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 初始化角色
 * @date: 2020/2/23
 * @time: 16:37
 * @author: 李志鹏
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class RoleInitialize implements ApplicationRunner {

    @Autowired
    private RoleService roleService;

    private Resource roleData = new ClassPathResource("static/role.json");

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void run(ApplicationArguments args) throws IOException {
        QueryResult<Role> result = roleService.query(1, 1, null);
        if (result.isEmpty()) {
            roleService.importDataFromJson(IoUtil.read(roleData.getInputStream(), Charset.defaultCharset()));
            log.info("初始化角色信息成功");
            SysStatic.needInit = true;
        }
    }
}
