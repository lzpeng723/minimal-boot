package com.lzpeng.project.${moduleName}.repository;

import com.lzpeng.framework.web.repository.${entityType}Repository;
import ${fullClassName};
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ${chineseClassName} 数据层
 * @date : ${.now?date}
 * @time : ${.now?time}
 * @author : 李志鹏
 */
@Api(tags = "${chineseClassName} Entity")
public interface ${simpleClassName}Repository extends ${entityType}Repository<${simpleClassName}> {

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
