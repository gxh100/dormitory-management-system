package com.henan.dms.utils;

import lombok.Data;

@Data
public class ResultUtil {
    private Integer code;
    private Boolean status;
    private String message;
    private Object data;

    private ResultUtil() {}

    private ResultUtil(Integer code, Boolean status, String msg, Object data) {
        this.code = code;
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    public static ResultUtil isSuccess(Object data) {
        return new ResultUtil(20000, true, "操作成功", data);
    }

    public static ResultUtil isSuccess(String msg, Object data) {
        return new ResultUtil(20000, true, msg, data);
    }

    public static ResultUtil isFail(Integer code, String msg) {
        return new ResultUtil(code, false, msg, null);
    }
}