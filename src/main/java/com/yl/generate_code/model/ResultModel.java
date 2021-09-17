package com.yl.generate_code.model;

import java.io.Serializable;

public class ResultModel implements Serializable {
    private Integer code;
    private String msg;
    private Object obj;

    public static ResultModel success(String msg,Object obj) {
        return new ResultModel(200,msg,obj);
    }
    public static ResultModel success(String msg) {
        return new ResultModel(200,msg,null);
    }
    public static ResultModel fail(String msg,Object obj) {
        return new ResultModel(500,msg,obj);
    }
    public static ResultModel fail(String msg) {
        return new ResultModel(500,msg,null);
    }

    private ResultModel() {

    }

    public ResultModel(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
