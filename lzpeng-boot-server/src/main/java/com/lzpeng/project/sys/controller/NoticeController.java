package com.lzpeng.project.sys.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.BaseControllerImpl;
import com.lzpeng.project.sys.domain.Notice;
import com.lzpeng.project.sys.domain.NotificationRecord;
import com.lzpeng.project.sys.service.NoticeService;
import com.lzpeng.project.tool.domain.TableInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 通知 控制层
 *
 * @author : 李志鹏
 * @date : 2020-5-16
 * @time : 21:03:56
 */
@Slf4j
@RestController
@RequestMapping("/sys/notice")
@Api(tags = "通知管理接口", value = "通知管理，提供通知的增、删、改、查")
public class NoticeController extends BaseControllerImpl<Notice> {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "sys";
    /**
     * 类名称
     */
    private static final String CLASS_NAME = "notice";
    /**
     * 通知列表权限
     */
    private static final String LIST_PERM = MODULE_NAME + ":" + CLASS_NAME + ":list";
    /**
     * 通知查询权限
     */
    private static final String QUERY_PERM = MODULE_NAME + ":" + CLASS_NAME + ":query";
    /**
     * 通知新增权限
     */
    private static final String ADD_PERM = MODULE_NAME + ":" + CLASS_NAME + ":add";
    /**
     * 通知删除权限
     */
    private static final String DELETE_PERM = MODULE_NAME + ":" + CLASS_NAME + ":delete";
    /**
     * 通知修改权限
     */
    private static final String EDIT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":edit";
    /**
     * 通知导出权限
     */
    private static final String EXPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":export";
    /**
     * 通知导入权限
     */
    private static final String IMPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":import";

    /**
     * 通知Service
     */
    private NoticeService noticeService;

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.baseService = noticeService;
        this.baseService = noticeService;
        this.noticeService = noticeService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取通知的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存通知")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<Notice> save(@Valid @RequestBody Notice model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除通知")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新通知")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<Notice> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestBody Notice model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有通知")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Notice>> findAll(Notice model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询通知列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<Notice>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, Notice model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询通知")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<Notice> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入通知")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<Notice>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出通知到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestParam(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作通知")
    @PreAuthorize("hasAuthority('" + ADD_PERM + "') and hasAuthority('" + EDIT_PERM + "') and hasAuthority('" + DELETE_PERM + "')")
    public Result batch(@RequestBody BatchModel<Notice> batch) {
        return super.batch(batch);
    }

    @PostMapping("/sendNotice/{noticeId:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("发送通知")
    @PreAuthorize("isAuthenticated()")
    public Result<Void> sendNotice(@ApiParam("通知id") @PathVariable String noticeId, @ApiParam("用户id数组") @RequestBody String[] receiverIds) {
        noticeService.sendNotice(noticeId, receiverIds);
        return ResultUtil.success();
    }

    @GetMapping("/receiveNotice")
    @ApiOperation("接收通知")
    @PreAuthorize("isAuthenticated()")
    public Result<List<NotificationRecord>> receiveNotice() {
        List<NotificationRecord> notificationRecords = noticeService.receiveNotice();
        return ResultUtil.success(notificationRecords);
    }


}