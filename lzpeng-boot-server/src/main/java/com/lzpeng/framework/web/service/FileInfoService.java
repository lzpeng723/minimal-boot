package com.lzpeng.framework.web.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.lzpeng.framework.domain.FileInfo;
import com.lzpeng.framework.web.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @date: 2020/3/10
 * @time: 23:18
 * @author:   李志鹏
 * https://blog.csdn.net/guotao15285007494/article/details/84974989
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class FileInfoService {

    @Value("${lzpeng.boot.upload.path}")
    private String fileUploadPath;

    @Value("${logging.file.name}")
    private String logFilePath;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @PostConstruct
    public void init() throws IOException {
        Path path = Paths.get(fileUploadPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        this.fileUploadPath = path.toFile().getCanonicalPath();
    }


    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public FileInfo uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String mainName = FileUtil.mainName(originalFilename);
        String extName = FileUtil.extName(originalFilename);
        String contentType = file.getContentType();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(mainName);
        fileInfo.setExtension(extName);
        fileInfo.setOriginalFileName(originalFilename);
        fileInfo.setSize(size);
        fileInfo.setPath(fileUploadPath);
        fileInfo.setContentType(contentType);
        fileInfo = fileInfoRepository.save(fileInfo);
        String fileName = String.join(".", fileInfo.getId(), fileInfo.getExtension());
        Path path = Paths.get(fileUploadPath, fileName);
        try {
            file.transferTo(path.toFile());
        } catch (Exception e) {
            e.printStackTrace();
            fileInfoRepository.deleteById(fileInfo.getId());
        }
        return fileInfo;
    }

    /**
     * 下载文件
     *
     * @param id
     * @return
     */
    public void downloadFile(String id, HttpServletResponse response) throws IOException {
        Optional<FileInfo> optional = fileInfoRepository.findById(id);
        if (optional.isPresent()) {
            FileInfo fileInfo = optional.get();
            String fileName = String.join(".", fileInfo.getId(), fileInfo.getExtension());
            Path path = Paths.get(fileUploadPath, fileName);
            try (InputStream inputStream = new FileInputStream(path.toFile());
                 OutputStream outputStream = response.getOutputStream()) {
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                //ContentDisposition contentDisposition = ContentDisposition.builder("inline").filename(fileInfo.getOriginalFileName()).build(); //在线预览
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileInfo.getOriginalFileName()).build(); //下载
                response.addHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
                IoUtil.copy(inputStream, outputStream);
                outputStream.flush();
            }
        } else {
            response.setContentType("text/plain;charset=utf-8");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().println("获取文件失败,文件id: " + id);
        }
    }

    /**
     * 下载日志文件
     *
     * @return
     */
    public void downloadLogFile(HttpServletResponse response) throws IOException {
        File file = Paths.get(logFilePath).toFile();
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            //ContentDisposition contentDisposition = ContentDisposition.builder("inline").filename(fileInfo.getOriginalFileName()).build(); //在线预览
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(file.getName()).build(); //下载
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            IoUtil.copy(inputStream, outputStream);
            outputStream.flush();
        }

    }

    /**
     * 下载文件
     *
     * @param file     待下载的文件
     * @param response 响应
     * @return
     */
    public void downloadFile(File file, HttpServletResponse response) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
            downloadFile(file.getName(), inputStream, response);
        } catch (IOException e) {
            response.setContentType("text/plain;charset=utf-8");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().println("下载文件失败,文件路径: " + file.getCanonicalPath());
        }
    }

    /**
     * 下载文件
     *
     * @param fileName    待下载的文件名
     * @param inputStream 待下载的流
     * @param response    响应
     * @return
     */
    public void downloadFile(String fileName, InputStream inputStream, HttpServletResponse response) throws IOException {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            //ContentDisposition contentDisposition = ContentDisposition.builder("inline").filename(fileName).build(); //在线预览
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName).build(); //下载
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            IoUtil.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            response.setContentType("text/plain;charset=utf-8");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().println("下载文件失败,文件: " + fileName);
        }
    }

}
