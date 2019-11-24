package com.services.dpidcalarm.utils;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/5 0005 21:33:42
 */
public class MyFieldError
{
    private String field;
    private String defaultMessage;

    public String getField()
    {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}