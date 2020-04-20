package com.lzpeng.project.sys.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 岗位 业务层
 * @date: 2020-3-20
 * @time: 16:03:56
 * @author: 李志鹏
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class PositionService extends AbstractPositionService {


}
