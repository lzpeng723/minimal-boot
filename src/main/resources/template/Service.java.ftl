package com.lzpeng.project.${moduleName}.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Generated;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
* ${chineseClassName} 业务层
* @date : ${.now?date}
* @time : ${.now?time}
* @author : 李志鹏
*/
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
@Generated(value = "com.lzpeng.project.tool.service.GenService", date = "${.now?date} ${.now?time}", comments = "${chineseClassName} 业务层")
public class ${simpleClassName}Service extends Abstract${simpleClassName}Service {

}
