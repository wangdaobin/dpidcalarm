package com.services.dpidcalarm.utils;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/5 0005 21:33:24
 */

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandleUtils
{
    public static List<MyFieldError> errorHandle(List<FieldError> errors)
    {
        List result = new ArrayList();
        for (FieldError error : errors) {
            MyFieldError myFieldError = new MyFieldError();
            myFieldError.setDefaultMessage(error.getDefaultMessage());
            myFieldError.setField(error.getField());
            result.add(myFieldError);
        }
        return result;
    }
}