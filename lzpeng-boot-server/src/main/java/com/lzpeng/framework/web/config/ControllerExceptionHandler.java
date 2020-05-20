/**
 * 
 */
package com.lzpeng.framework.web.config;

import com.lzpeng.common.response.CommonCode;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Filter -> ControllerAdvice -> Interceptor -> AOP
 * 自定义异常响应
 * RestControllerAdvice 在 Interceptor 之前执行
 * Interceptor 中的 afterCompletion 将获取不到 RestControllerAdvice 处理过的异常
 * @author 李志鹏
 *
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 参数解析异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleValidateException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResultUtil.create(CommonCode.ARGUMENT_FAIL, errors);
    }

    /**
     * 文件大小超过限制
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleValidateException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        // 最大字节数
        long maxUploadSize = e.getMaxUploadSize();
        // 不知道为什么获取出来是 -1
        if (maxUploadSize == -1) {
            throw e;
        }
        // 转为 MB ，忽略 1kb 以下
        double mb = (maxUploadSize >> 10) / (1 << 10);
        return ResultUtil.create(CommonCode.FILE_SIZE_LIMIT, String.format("只允许上传 %.3fMB 以内的文件", mb));
    }

    /**
     * 没有权限
     * @param e
     * @return
     */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Result handleException(AccessDeniedException e) {
        e.printStackTrace();
        return ResultUtil.create(CommonCode.ACCESS_DENIED, e.getMessage());
	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result handleException(Exception e) {
        e.printStackTrace();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        Throwable t = e;
        map.add("message", e.getMessage());
        while (t != null) {
            map.add("e", e.getClass().getName());
            StackTraceElement[] st = t.getStackTrace();
            for (StackTraceElement stackTraceElement : st) {
                map.add("at", stackTraceElement.toString());
            }
            while ((t = t.getCause()) != null) {
                map.add("causeBy", t.toString());
            }
        }
        return ResultUtil.create(CommonCode.FAIL, map);
	}

}
