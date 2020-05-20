package com.lzpeng.project.monitor.vo.server;

import lombok.Getter;
import oshi.util.FormatUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JVM相关信息
 *
 * @author 李志鹏
 */
@Getter
public class Jvm {

    /**
     * Java 运行时版本
     */
    private String version;

    /**
     * Java 安装目录
     */
    private String home;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * Java 虚拟机实现名称
     */
    private String name;
    /**
     * Java 类格式版本
     */
    private String classVersion;

    /**
     * JVM 已申请内存
     */
    private long totalMemory;

    /**
     * JVM 最大可申请内存
     */
    private long maxMemory;

    /**
     * JVM 空闲内存
     */
    private long freeMemory;

    /**
     * Jvm 启动时间
     */
    private long startTime;

    /**
     * 堆区内存
     */
    private Map<String, MemoryUsage> heapMemory = new HashMap<>();

    /**
     * 非堆区内存
     */
    private Map<String, MemoryUsage> noHeapMemory = new HashMap<>();

    public Jvm() {
        this.version = System.getProperty("java.version");
        this.home = System.getProperty("java.home");
        this.userDir = System.getProperty("user.dir");
        this.name = System.getProperty("java.vm.name");
        this.classVersion = System.getProperty("java.class.version");
        this.startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        this.totalMemory = Runtime.getRuntime().totalMemory();
        this.maxMemory = Runtime.getRuntime().totalMemory();
        this.freeMemory = Runtime.getRuntime().freeMemory();
        ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            switch (pool.getType()) {
                case HEAP:
                    heapMemory.put(pool.getName(), pool.getUsage());
                    break;
                case NON_HEAP:
                    noHeapMemory.put(pool.getName(), pool.getUsage());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("版本: ").append(version).append("\r\n");
        builder.append("安装目录: ").append(home).append("\r\n");
        builder.append("实现名称: ").append(name).append("\r\n");
        builder.append("类格式版本: ").append(classVersion).append("\r\n");
        builder.append("项目路径: ").append(userDir).append("\r\n");
        builder.append("当前内存: ").append(FormatUtil.formatBytes(totalMemory)).append("\r\n");
        builder.append("最大内存: ").append(FormatUtil.formatBytes(maxMemory)).append("\r\n");
        builder.append("空闲内存: ").append(FormatUtil.formatBytes(freeMemory)).append("\r\n");
        builder.append("启动时间: ").append(new Date(startTime)).append("\r\n");
        builder.append("堆区内存: \r\n");
        for (Entry<String, MemoryUsage> entry : heapMemory.entrySet()) {
            MemoryUsage memoryUsage = entry.getValue();
            builder.append("\t").append(entry.getKey()).append(" :\r\n");
            builder.append("\t\t初始内存 :").append(FormatUtil.formatBytes(memoryUsage.getInit())).append(" :\r\n");
            builder.append("\t\t已用内存 :").append(FormatUtil.formatBytes(memoryUsage.getUsed())).append(" :\r\n");
            builder.append("\t\t可用内存 :").append(FormatUtil.formatBytes(memoryUsage.getCommitted())).append(" :\r\n");
            builder.append("\t\t最大内存 :").append(FormatUtil.formatBytes(memoryUsage.getMax())).append(" :\r\n");
        }
        builder.append("非堆区内存: \r\n");
        for (Entry<String, MemoryUsage> entry : noHeapMemory.entrySet()) {
            MemoryUsage memoryUsage = entry.getValue();
            builder.append("\t").append(entry.getKey()).append(" :\r\n");
            builder.append("\t\t初始内存 :").append(FormatUtil.formatBytes(memoryUsage.getInit())).append(" :\r\n");
            builder.append("\t\t已用内存 :").append(FormatUtil.formatBytes(memoryUsage.getUsed())).append(" :\r\n");
            builder.append("\t\t可用内存 :").append(FormatUtil.formatBytes(memoryUsage.getCommitted())).append(" :\r\n");
            builder.append("\t\t最大内存 :").append(FormatUtil.formatBytes(memoryUsage.getMax())).append(" :\r\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Jvm());
    }
}
