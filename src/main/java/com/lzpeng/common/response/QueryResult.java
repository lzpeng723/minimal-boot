package com.lzpeng.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 列表查询结果返回值
 * @date: 2020/2/1
 * @time: 23:14
 * @author: 李志鹏
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询结果")
public class QueryResult<T> {

    /**
     * 数据列表
     */
    @ApiModelProperty("数据列表")
    private List<T> list;

    /**
     * 数据总数
     */
    @ApiModelProperty("数据总数")
    private long total;

    /**
     * 当前第几页
     */
    @ApiModelProperty("当前第几页")
    private long page;

    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private long totalPage;

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return total == 0;
    }
}
