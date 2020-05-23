package com.lzpeng.framework.web.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存Controller
 * @date: 2020/2/4
 * @time: 14:11
 * @author: 李志鹏
 */
@EnableCaching
@RestController
@RequestMapping("/framework/cache")
@Api(tags = "缓存管理接口", value = "缓存管理，提供缓存的查询和删除")
public class CacheController {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private CacheManager cacheManager;


    @GetMapping
    @ApiOperation("查询所有缓存")
    public Result<Map<String, Object>> getCache(){
        Map<String, Object> cacheMap = new HashMap<>();
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            cacheMap.put(cache.getName(), cache.getNativeCache());
        }
        return ResultUtil.success(cacheMap);
    }

    @GetMapping("/{cacheName}")
    @ApiOperation("根据缓存名称查询缓存")
    public Result<Map<String, Object>> getCache(@ApiParam(value = "缓存名称", required = true) @PathVariable("cacheName") String cacheName){
        Map<String, Object> cacheMap = new HashMap<>();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cacheMap.put(cache.getName(), cache.getNativeCache());
        }
        return ResultUtil.success(cacheMap);
    }
    @GetMapping("/{cacheName}/{key}")
    @ApiOperation("根据缓存名称和键查询缓存的值")
    public Result<Map<String, Object>> getCache(@ApiParam(value = "缓存名称", required = true) @PathVariable("cacheName") String cacheName, @ApiParam(value = "缓存的键名称", required = true) @PathVariable("key") String key){
        Map<String, Object> cacheMap = new HashMap<>();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cacheMap.put(key, cache.get(key));
        }
        return ResultUtil.success(cacheMap);
    }

    @DeleteMapping
    @ApiOperation("删除所有缓存")
    public Result<Void> deleteCache(){
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            cache.clear();
        }
        return ResultUtil.success();
    }

    @DeleteMapping("/{cacheName}")
    @ApiOperation("根据缓存名称删除缓存")
    public<Void> Result deleteCache(@ApiParam(value = "缓存名称", required = true) @PathVariable("cacheName") String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
        return ResultUtil.success();
    }

    @DeleteMapping("/{cacheName}/{key}")
    @ApiOperation("根据缓存名称和键删除缓存的值")
    public Result<Void> deleteCache(@ApiParam(value = "缓存名称", required = true) @PathVariable("cacheName") String cacheName, @ApiParam(value = "缓存的键名称", required = true) @PathVariable("key") String key){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evictIfPresent(key);
        }
        return ResultUtil.success();
    }

}
