package com.lzpeng.project.monitor.vo.server;

import cn.hutool.system.oshi.OshiUtil;
import lombok.Getter;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.util.Util;

/**
 * CPU相关信息
 * 
 * @author 李志鹏
 */
@Getter
public class Cpu {
	
	/**
     * 	CPU 名称
     */
    private String name;
    /**
     * 	CPU 核心数
     */
    private int coreNum;
    /**
     * 	CPU 线程数
     */
    private int threadNum;
    /**
     * 	CPU 序列号 如果存在
     */
    private String processorId;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;
    
    
	

	public Cpu() {
		super();
		CentralProcessor processor = OshiUtil.getProcessor();
		this.name = processor.getProcessorIdentifier().getName();
		this.coreNum = processor.getPhysicalProcessorCount();
		this.threadNum = processor.getLogicalProcessorCount();
		this.processorId = processor.getProcessorIdentifier().getProcessorID();
		int oshiWaitSecond = 1000;
		long[] prevTicks = processor.getSystemCpuLoadTicks();
		Util.sleep(oshiWaitSecond);
		long[] ticks = processor.getSystemCpuLoadTicks();
		long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
		long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
		long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
		long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
		long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
		long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
		long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
		this.total = totalCpu;
		this.sys = cSys;
		this.free = idle;
		this.used = user;
		this.wait = iowait;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("名称: ").append(name).append("\r\n");
		builder.append("核心数: ").append(coreNum).append("\r\n");
		builder.append("线程数: ").append(threadNum).append("\r\n");
		builder.append("序列号: ").append(processorId).append("\r\n");
		builder.append("系统使用率: ").append(String.format("%.2f%%", sys/total*100)).append("\r\n");
		builder.append("用户使用率: ").append(String.format("%.2f%%", used/total*100)).append("\r\n");
		builder.append("当前等待率: ").append(String.format("%.2f%%", wait/total*100)).append("\r\n");
		builder.append("当前空闲率: ").append(String.format("%.2f%%", free/total*100)).append("\r\n");
		return builder.toString();
	}


	public static void main(String[] args) {
		System.out.println(new Cpu());
	}
}
