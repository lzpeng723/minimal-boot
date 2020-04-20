package com.lzpeng.project.monitor.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.BaseControllerImpl;
import com.lzpeng.project.monitor.domain.Job;
import com.lzpeng.project.monitor.service.JobService;
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
 * 定时任务 控制层
 * @date: 2020-4-6
 * @time: 17:39:39
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/monitor/job")
@Api(tags = "定时任务管理接口", value = "定时任务管理，提供定时任务的增、删、改、查")
public class JobController extends BaseControllerImpl<Job> {

    private static final String MODULE_NAME = "monitor";
    private static final String CLASS_NAME = "job";
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list"; // 定时任务列表权限
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query"; // 定时任务查询权限
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add"; // 定时任务新增权限
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete"; // 定时任务删除权限
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit"; // 定时任务修改权限
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export"; // 定时任务导出权限
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import"; // 定时任务导入权限

    protected JobService jobService;

    @Autowired
    public void setJobService(JobService jobService) {
        this.baseService = jobService;
        this.baseService = jobService;
        this.jobService = jobService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取定时任务的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存定时任务")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<Job> save(@Valid @RequestBody Job model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除定时任务")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新定时任务")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<Job> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestBody Job model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有定时任务")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Job>> findAll(Job model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询定时任务列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<Job>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, Job model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询定时任务")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<Job> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作定时任务")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<Job> batch) {
        return super.batch(batch);
    }


    @PostMapping("/execute/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("立即执行定时任务")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> execute(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) throws Exception {
        jobService.executeJob(id);
        return ResultUtil.success("执行成功");
    }

    @PostMapping("/pause/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("暂停定时任务")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> pause(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) throws Exception {
        jobService.pauseJob(id);
        return ResultUtil.success("暂停成功");
    }

    @PostMapping("/stop/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("停止定时任务")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> stop(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) throws Exception {
        jobService.stopJob(id);
        return ResultUtil.success("停止成功");
    }

    @PostMapping("/resume/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("恢复定时任务")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> resume(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) throws Exception {
        jobService.resumeJob(id);
        return ResultUtil.success("恢复成功");
    }

    @PostMapping("/start/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("开启定时任务")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> start(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) throws Exception {
        jobService.registerJob(id);
        return ResultUtil.success("开启成功");
    }


    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入定时任务")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<Job>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出定时任务到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }
}