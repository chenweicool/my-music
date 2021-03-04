package com.mymusic.formvo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 测试一下整个py的这个密码的问题
 */
public class PasswordTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "123456";
        String passwordDb = passwordEncoder.encode(password);
        boolean flag = passwordEncoder.matches(password, passwordDb);
        System.out.println(passwordDb);
        System.out.println("校验后的结果是："+flag);
        String echderPassword = "$2a$10$xZYSXk.eIyoV9RjFiE7pEuEVYp6U/J.g1B6yG/htWSpbw0VOHPHLW";
        boolean result = passwordEncoder.matches("abcd1234", echderPassword);
        System.out.println(result);
    }
}
