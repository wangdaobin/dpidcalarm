package com.services.dpidcalarm.base.controller;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/5 0005 21:30:31
 */

import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.base.service.BaseService;
import com.services.dpidcalarm.utils.AjaxBody;
import com.services.dpidcalarm.utils.ErrorHandleUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

public abstract class BaseController<T>
{
    public abstract BaseService<T> baseService();

    @GetMapping({"{id}"})
    @ResponseBody
    public AjaxBody findById(@PathVariable("id") Integer id)
    {
        Object t = baseService().findById(id);
        return AjaxBody.success(t);
    }
    @PostMapping
    @ResponseBody
    public AjaxBody add(@Valid @RequestBody T t, BindingResult result) {
        if (result.hasErrors()){
            return AjaxBody.fail(ErrorHandleUtils.errorHandle(result.getFieldErrors()));
        }

        try
        {
            baseService().add(t);
            return AjaxBody.success();
        } catch (DuplicateKeyException e) {
            return AjaxBody.fail("存在重复的对象，添加失败"); } catch (Exception e) {
        }
        return AjaxBody.fail();
    }

    @GetMapping
    @ResponseBody
    public AjaxBody pageList(T t, @RequestParam(value="page", required=false) Integer pageNum, @RequestParam(value="rows", required=false) Integer pageSize)
    {
        PageInfo pageInfo = baseService().pageList(t, pageNum, pageSize);
        return AjaxBody.success(pageInfo);
    }
    @PutMapping
    @ResponseBody
    public AjaxBody update(@Valid @RequestBody T t, BindingResult result) {
        if (result.hasErrors()){
            return AjaxBody.fail(ErrorHandleUtils.errorHandle(result.getFieldErrors()));
        }

        try
        {
            baseService().update(t);
            return AjaxBody.success();
        } catch (DuplicateKeyException e) {
            return AjaxBody.fail("存在重复的对象,更新失败"); } catch (Exception e) {
        }
        return AjaxBody.fail(); }

    @DeleteMapping({"{id}"})
    @ResponseBody
    public AjaxBody delete(@PathVariable("id") Integer id) {
        try {
            baseService().delete(id);
            return AjaxBody.success();
        } catch (Exception e) {
            return AjaxBody.fail(e.getMessage());
        }

    }
}