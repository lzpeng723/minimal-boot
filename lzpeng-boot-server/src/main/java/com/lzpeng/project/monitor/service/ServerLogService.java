package com.lzpeng.project.monitor.service;

import cn.hutool.core.io.FileUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.framework.web.service.FileInfoService;
import com.lzpeng.project.monitor.vo.ServerLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 方法日志 业务层
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@Service
public class ServerLogService {

    @Value("${logging.file.name}")
    private String logFilePath;

    /**
     * 日志文件目录
     */
    private File logFileContent;

    @Autowired
    private FileInfoService fileInfoService;

    @PostConstruct
    private void init() throws IOException {
        Path path = Paths.get(logFilePath).getParent(); // 日志文件所在文件夹
        this.logFileContent = path.toFile().getCanonicalFile(); // 转化为真实路径

    }

    /**
     * 查询后台日志列表
     *
     * @param model
     * @return
     */
    public List<ServerLog> findAll(ServerLog model) {
        File[] logFiles = logFileContent.listFiles(); // 所有日志文件
        QueryResult<ServerLog> queryResult = query(1, logFiles.length, model);
        List<ServerLog> serverLogList = queryResult.getList();
        return serverLogList;
    }

    /**
     * 分页查询日志文件
     *
     * @param page
     * @param size
     * @param model
     * @return
     */
    public QueryResult<ServerLog> query(int page, int size, ServerLog model) {
        File[] logFiles = logFileContent.listFiles(); // 所有日志文件
        int count = logFiles.length; // 日志文件个数
        int skipCount = (page - 1) * size; // 需要跳过的文件个数
        int totalPages = (int) Math.ceil(count / (double) size); // 日志文件总页数
        if (skipCount > count) {
            page = totalPages;
            skipCount = (page - 1) * size; // 修改需要跳过的文件个数
        }
        List<ServerLog> serverLogList = Stream.of(logFiles)
                .sorted(Comparator.comparing(FileUtil::lastModifiedTime, Comparator.reverseOrder()))
                .skip(skipCount)
                .limit(size)
                .map(logFile -> {
                    ServerLog serverLog = new ServerLog();
                    serverLog.setFileName(logFile.getName());
                    serverLog.setSize(logFile.length());
                    serverLog.setLastModifiedTime(FileUtil.lastModifiedTime(logFile));
                    try {
                        serverLog.setCanonicalPath(logFile.getCanonicalPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return serverLog;
                })
                .collect(Collectors.toList());
        return new QueryResult(serverLogList, count, page, totalPages);
    }

    /**
     * 下载日志文件
     * 待实现断点续传
     * @param fileName 日志文件名称
     * @param response 响应
     */
    public void downloadLogFile(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(logFileContent, fileName);
        fileInfoService.downloadFile(file, response);
    }
}
