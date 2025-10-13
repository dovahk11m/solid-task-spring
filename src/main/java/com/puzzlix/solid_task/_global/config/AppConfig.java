package com.puzzlix.solid_task._global.config;

/*
앱컨피그 <-> 웹컨피그
앱 전체에 관련된 설정

 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //IoC 대상
public class AppConfig {

    @Bean //IoC
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
