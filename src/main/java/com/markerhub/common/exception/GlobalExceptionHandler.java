package com.markerhub.common.exception;

import com.markerhub.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author zhang Bowen
 * @date 2021-10-24 11:16
 * 全局异常处理
 *
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.error("运行时异常：----------------",e.getMessage());
        return Result.fail(e.getMessage());
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handler(AccessDeniedException e){
        log.info("security权限不足：----------------",e.getMessage());
        return Result.fail(e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        log.info("实体校验异常：----------------",e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalAccessError.class)
    public Result handler(IllegalAccessError e){
        log.error("非法数据访问异常：----------------",e.getMessage());
        return Result.fail(e.getMessage());
    }
}
