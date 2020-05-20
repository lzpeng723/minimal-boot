package com.lzpeng.project.monitor.vo.server;

import cn.hutool.system.HostInfo;
import lombok.Getter;

/**
 * 系统相关信息
 *
 * @author 李志鹏
 */
@Getter
public class Sys {
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;


    public Sys() {
        HostInfo hostInfo = new HostInfo();
        this.osName = System.getProperty("os.name");
        this.osArch = System.getProperty("os.arch");
        this.userDir = System.getProperty("user.dir");
        this.computerIp = hostInfo.getAddress();
        this.computerName = hostInfo.getName();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("操作系统: ").append(osName).append("\r\n");
        builder.append("系统架构: ").append(osArch).append("\r\n");
        builder.append("项目路径: ").append(userDir).append("\r\n");
        builder.append("服务器名称: ").append(computerName).append("\r\n");
        builder.append("服务器IP: ").append(computerIp).append("\r\n");
        return builder.toString();
    }


    public static void main(String[] args) {
        System.out.println(new Sys());
    }
}
