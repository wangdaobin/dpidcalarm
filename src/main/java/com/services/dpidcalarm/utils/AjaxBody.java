package com.services.dpidcalarm.utils;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/5 0005 21:31:08
 */

public class AjaxBody
{
    private String message;
    private String status;
    private Object data;
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String SUCCESS_MESSAGE = "操作成功";
    public static final String FAIL_MESSAGE = "操作失败";

    public AjaxBody(String message, String status, Object data)
    {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static AjaxBody success(Object data) {
        return new AjaxBody("操作成功", "success", data);
    }

    public static AjaxBody success(String message) {
        return new AjaxBody(message, "success", null);
    }

    public static AjaxBody success() {
        return new AjaxBody("操作成功", "success", null);
    }

    public static AjaxBody fail(String message) {
        return new AjaxBody(message, "fail", null);
    }

    public static AjaxBody fail(Object data) {
        return new AjaxBody("操作失败", "fail", data);
    }

    public static AjaxBody fail() {
        return new AjaxBody("操作失败", "fail", null);
    }
}