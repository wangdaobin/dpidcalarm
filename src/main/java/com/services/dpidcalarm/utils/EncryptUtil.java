package com.services.dpidcalarm.utils;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/10 0010 13:50:05
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 5. * @author XUYI
 6. * Spring Security 3.1 PasswordEncoder
 7. */
 public class EncryptUtil {
     //从配置文件中获得
     private static final String SITE_WIDE_SECRET = "my-secret-key";
     private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

      public static String encrypt(String rawPassword) {
          return encoder.encode(rawPassword);
      }

      public static boolean match(String rawPassword, String password) {
          return encoder.matches(rawPassword, password);
      }
      public static void main(String[] args) {
       System.out.println(EncryptUtil.encrypt("gl"));
       System.out.println(EncryptUtil.encrypt("gl"));
       System.out.println(EncryptUtil.encrypt("gl"));
       System.out.println(EncryptUtil.encrypt("gl"));
       System.out.println(EncryptUtil.encrypt("gl"));
       boolean b = EncryptUtil.match("gl","$2a$10$5MXcpIo1LUKY8C97phgaW.IfaBAfLREJ.JP0E3AbsbsNqeoDA/i8i");
          System.out.println(b);
         //但是把每次结果拿出来进行match，你会发现可以得到true。
       }
 }
