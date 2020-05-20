package com.lzpeng.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @date: 2020/3/9
 * @time: 19:25
 * @author:   李志鹏
 */
@Data
@AllArgsConstructor
public class RequestLogAggregation {

    @Id
    private String uri;

    /**
     * 平均访问时间
     */
    private long avgCostTime;

    /**
     * 最慢访问时间
     */
    private long maxCostTime;

    /**
     * 最快访问时间
     */
    private long minCostTime;

    /**
     * 访问次数
     */
    private long count;
}
