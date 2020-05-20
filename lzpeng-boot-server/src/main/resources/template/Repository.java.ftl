package com.lzpeng.project.${moduleName}.repository;

import com.lzpeng.framework.web.repository.${entityType}Repository;
import ${fullClassName};<#if entityType=="LeftTreeRightTable">
import ${leftTree.fullClassName};</#if>
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Generated;

/**
* ${chineseClassName} 数据层
* @date : ${.now?date}
* @time : ${.now?time}
* @author : 李志鹏
*/
@Api(tags = "${chineseClassName} Entity")
@Generated(value = "com.lzpeng.project.tool.service.GenService", date = "${.now?date} ${.now?time}", comments = "${chineseClassName} 数据层")
public interface ${simpleClassName}Repository extends ${entityType}Repository<<#if entityType=="LeftTreeRightTable">${leftTree.simpleClassName}, </#if>${simpleClassName}> {

/**
* 更新${chineseClassName}状态
* @param id ${chineseClassName}id
* @param enabled ${chineseClassName}状态
* @return
*/
@Override
@Modifying
    @Query("UPDATE ${simpleClassName} t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);


}
