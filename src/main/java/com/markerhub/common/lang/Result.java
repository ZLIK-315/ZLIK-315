package com.markerhub.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang Bowen
 * @date 2021-10-23 21:01
 * 返回结果集封装
 */
@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return success(200,"操作成功",data);
    }

    public static Result success(int code,String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg) {
        return fail(400,msg,null);
    }

    public static Result fail(int code,String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
