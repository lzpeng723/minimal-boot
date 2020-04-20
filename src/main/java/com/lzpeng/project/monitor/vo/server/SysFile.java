package com.lzpeng.project.monitor.vo.server;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.system.oshi.OshiUtil;
import lombok.Getter;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

/**
 * 系统文件相关信息
 *
 * @author 李志鹏
 */
@Getter
public class SysFile {
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 盘符名称
     */
    private String label;

    /**
     * 总大小
     */
    private long total;

    /**
     * 剩余大小
     */
    private long free;


    private SysFile() {

    }


    public static List<SysFile> getSysFiles() {
        FileSystem fileSystem = OshiUtil.getOs().getFileSystem();
        OSFileStore[] fileStores = fileSystem.getFileStores();
        List<SysFile> sysFiles = new ArrayList<>(fileStores.length);
        for (OSFileStore fs : fileStores) {
            SysFile sysFile = new SysFile();
            sysFile.dirName = fs.getMount();
            sysFile.sysTypeName = fs.getType();
            sysFile.label = fs.getLabel();
            sysFile.total = fs.getTotalSpace();
            sysFile.free = fs.getUsableSpace();
            sysFiles.add(sysFile);
        }
        return sysFiles;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuilder builder = new StringBuilder();
        builder.append("盘符: ").append(dirName).append("\r\n");
        builder.append("文件类型: ").append(sysTypeName).append("\r\n");
        builder.append("盘名: ").append(label).append("\r\n");
        builder.append("总大小: ").append(FormatUtil.formatBytes(total)).append("\r\n");
        builder.append("已用大小: ").append(FormatUtil.formatBytes(total - free)).append("\r\n");
        builder.append("剩余大小: ").append(FormatUtil.formatBytes(free)).append("\r\n");
        builder.append("使用率: ").append(String.format("%.2f%%", (total - free) * 100.0 / total)).append("\r\n");
        return builder.toString();
    }


    public static void main(String[] args) {
        List<SysFile> sysFiles = SysFile.getSysFiles();
        for (SysFile sysFile : sysFiles) {
            System.out.println(sysFile);
        }
    }
}
