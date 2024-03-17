package com.example.zhaoshichuan.exception;

import com.example.zhaoshichuan.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，
 * 用于捕获所有Controller抛出的异常，避免每个Controller都写一个异常处理方法(TRY-CATCH)
 * */
@RestControllerAdvice // 表示这是一个全局的异常处理类,组合注解，包含@ResponseBody，把对象封装成JSo格式
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error("对不起，操作失败，清联系管理员");
    }
}
