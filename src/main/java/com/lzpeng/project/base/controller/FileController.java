package com.lzpeng.project.base.controller;


import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.project.base.domain.FileInfo;
import com.lzpeng.project.base.service.FileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件上传下载
 * @date: 2019/12/7
 * @time: 17:01
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/base/file")
@Api(tags = "文件处理接口", value = "文件处理，提供文件的上传和下载")
public class FileController {

    @Autowired
    private FileInfoService fileInfoService;


    @PostMapping
    @ApiOperation("上传文件")
    @PreAuthorize("isAuthenticated()")
    public Result<FileInfo> uploadFile(MultipartFile file) throws IOException {
        FileInfo fileInfo = fileInfoService.uploadFile(file);
        return ResultUtil.success(fileInfo);
    }

    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("下载文件")
    @PreAuthorize("isAuthenticated()")
    public void downloadFile(@ApiParam(value = "文件id", required = true) @PathVariable String id, HttpServletResponse response) throws IOException {
        fileInfoService.downloadFile(id, response);
    }

    @GetMapping("/log")
    @ApiOperation("下载服务器日志")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void downloadLogFile(HttpServletResponse response) throws IOException {
        fileInfoService.downloadLogFile(response);
    }
}
