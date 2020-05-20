package com.lzpeng.project.sys.config;

import cn.hutool.core.io.IoUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.service.DepartmentService;
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
 * 初始化部门
 * @date: 2020/5/7
 * @time: 21:08
 * @author: 李志鹏
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class DepartmentInitialize implements ApplicationRunner {

    @Autowired
    private DepartmentService departmentService;

    private Resource departmentData = new ClassPathResource("static/department.json");

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void run(ApplicationArguments args) throws IOException {
        QueryResult<Department> result = departmentService.query(1, 1);
        if (result.isEmpty()) {
            departmentService.importDataFromJson(IoUtil.read(departmentData.getInputStream(), Charset.defaultCharset()));
            log.info("初始化部门信息成功");
            SysStatic.needInit = true;
        }
    }
}
