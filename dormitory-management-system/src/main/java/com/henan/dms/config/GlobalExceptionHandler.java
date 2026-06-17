package com.henan.dms.config;

import com.henan.dms.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        e.printStackTrace();
        return ResultUtil.isFail(500, e.getMessage());
    }
}