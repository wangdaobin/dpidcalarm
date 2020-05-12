package com.services.dpidcalarm.config;

import com.services.dpidcalarm.sysManager.service.CustomUserDetailsService;
import com.services.dpidcalarm.utils.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description TODO
 * @Author Admin
 * @Date 2019/11/17 0017 下午 14:13
 */
@Configuration
@EnableWebSecurity
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private MyAuthenticationFailHandler authenticationFailHandler;
    /**
    * 配置密码的加密与解密
    * @return 返回加密的方式(比如MD5，BCryptPasswordEncoder：security自带的加密方式等)
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()          // 定义当需要用户登录时候，转到的登录页面。
                // 设置登录页面
                .loginPage("/html/login.html")
                // 自定义的登录接口
                .loginProcessingUrl("/login")
                .failureHandler(authenticationFailHandler)
                // 设置登陆成功页
                .defaultSuccessUrl("/html/main.html",true).permitAll()
                .and()
                // 开启logout功能，在使用WebSecurityConfigurerAdapter的时候默认开启
                .logout().permitAll()
                .logoutUrl("/logout")
                //指定登出成功跳转页面。默认/login?logout。这里我们使用默认配置
                .logoutSuccessUrl("/html/login.html")
                .and()
                .authorizeRequests()    // 定义哪些URL需要被保护、哪些不需要被保护
                // 设置所有人都可以访问登录页面
                    .antMatchers("/css/**","/js/**","/images/**","/plugins/**").permitAll()
                    .anyRequest()        // 任何请求,登录后可以访问
                    .authenticated()
                .and()
                .csrf().disable()// 关闭csrf防护
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.customUserDetailsService)
                .passwordEncoder(passwordEncoder());

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**","/js/**","/images/**");
    }
}
