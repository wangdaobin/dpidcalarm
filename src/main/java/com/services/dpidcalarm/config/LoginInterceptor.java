package com.services.dpidcalarm.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/5/12 0012 下午 21:26
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        if(!doLoginInterceptor(path, basePath) ){//是否进行登陆拦截
            return true;
        }

        //如果登录了，会把用户信息存进session
        HttpSession session = request.getSession();
        //List<User> users =  (List<User>) session.getAttribute("userList");
        List users = null;
        if(users==null){
            /*log.info("尚未登录，跳转到登录界面");
            response.sendRedirect(request.getContextPath()+"signin");*/

            String requestType = request.getHeader("X-Requested-With");
            if(requestType!=null && requestType.equals("XMLHttpRequest")){
                response.setHeader("sessionstatus","timeout");
                response.getWriter().print("LoginTimeout");
                return false;
            } else {
                System.out.println("尚未登录，跳转到登录界面");
                response.sendRedirect(request.getContextPath()+"/html/login.html");
            }
            return false;
        }
        return true;
    }

    /**
     * 是否进行登陆过滤
     * @param path
     * @param basePath
     * @return
     */
    private boolean doLoginInterceptor(String path,String basePath){
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        //设置不进行登录拦截的路径：登录注册和验证码
        notLoginPaths.add("html/login.html");

        if(notLoginPaths.contains(path)) {
            return false;
        }
        return true;
    }

}
