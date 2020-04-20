package com.lzpeng.project.monitor.vo.server;


import cn.hutool.system.oshi.OshiUtil;
import lombok.Getter;
import oshi.hardware.GlobalMemory;
import oshi.util.FormatUtil;

/**
 * 內存相关信息
 *
 * @author 李志鹏
 */
@Getter
public class Mem {
    /**
     * 内存总量
     */
    private long total;

    /**
     * 剩余内存
     */
    private long free;

    public Mem() {
        GlobalMemory memory = OshiUtil.getMemory();
        this.total = memory.getTotal();
        this.free = memory.getAvailable();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("总内存: ").append(FormatUtil.formatBytes(total)).append("\r\n");
        builder.append("已用内存: ").append(FormatUtil.formatBytes(total - free)).append("\r\n");
        builder.append("剩余内存: ").append(FormatUtil.formatBytes(free)).append("\r\n");
        builder.append("使用率: ").append(String.format("%.2f%%", (total - free) * 100.0 / total)).append("\r\n");
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Mem());
    }
}
