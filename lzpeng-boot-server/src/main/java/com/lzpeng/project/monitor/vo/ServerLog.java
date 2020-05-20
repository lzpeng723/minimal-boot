package com.lzpeng.project.monitor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @date: 2020/4/6
 * @time: 0:40
 * @author:   李志鹏
 * 方法日志
 */
@Data
@ApiModel("后台日志")
public class ServerLog implements Serializable {

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件真实路径")
    private String canonicalPath;

    @ApiModelProperty("文件字节数")
    private long size;

    @ApiModelProperty("文件最后修改时间")
    private Date lastModifiedTime;


}
