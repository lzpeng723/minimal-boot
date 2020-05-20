package com.lzpeng.project.tool.service;

import com.lzpeng.project.tool.domain.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 表信息 业务层
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class TableInfoService extends AbstractTableInfoService {

    /**
     * 查找所有表名
     * @return
     */
    public List<String> findTableNames(){
        return tableInfoRepository.findTableNames();
    }

    /**
     * 根据实体名称查询表的详细信息
     * @param className 实体名称
     * @return
     */
    public TableInfo findByClassName(String className){
        return tableInfoRepository.findByClassName(className);
    }


}
