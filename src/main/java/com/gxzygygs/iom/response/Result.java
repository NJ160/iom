package com.gxzygygs.iom.response;

import lombok.Data;
import lombok.val;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result{
    private static final long serialVersionUID = 1L;

    public int code;
    public String msg;
    public Map<String, Object> data;

    public Result() {
        this.code= HttpStatus.OK.value();
        this.msg = "success";
        this.data = new HashMap<>();
    }


    public Result(int code,String msg) {
        this.code= code;
        this.msg = msg;
        this.data = new HashMap<>();
    }

    public static Result error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result(code,msg);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result(HttpStatus.OK.value(),msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.data.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    public Result put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
