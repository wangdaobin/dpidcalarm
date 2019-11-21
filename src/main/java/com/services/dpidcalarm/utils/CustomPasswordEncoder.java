package com.services.dpidcalarm.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description TODO
 * @Author Admin
 * @Date 2019/11/18 0018 上午 0:22
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
