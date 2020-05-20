package com.lzpeng.project.monitor.vo;

import com.lzpeng.project.monitor.vo.server.*;
import lombok.Getter;

import java.util.List;

/**
 * 服务器相关信息
 *
 * @author 李志鹏
 */
@Getter
public class Server {
    /**
     * cpu 信息
     */
    private Cpu cpu = new Cpu();
    /**
     * jvm 信息
     */
    private Jvm jvm = new Jvm();
    /**
     * 内存 信息
     */
    private Mem mem = new Mem();
    /**
     * 系统 信息
     */
    private Sys sys = new Sys();
    /**
     * 系统文件 信息
     */
    private List<SysFile> sysFiles = SysFile.getSysFiles();
}
