package com.mymusic.jwt;


import com.mymusic.jwt.config.JwtAuthenticationTokenFilter;
import com.mymusic.jwt.model.JwtProperties;
import com.mymusic.jwt.service.MyUserDetailsService;
import com.mymusic.jwt.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnProperty(name = "mymusic.jwt.enabled", havingValue = "true")
@EnableConfigurationProperties(JwtProperties.class)
public class JWTAutoConfigure {

    @Resource
    private JwtProperties jwtProperties;


    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(jwtProperties);
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(
            JwtTokenUtil jwtTokenUtil,
            MyUserDetailsService myUserDetailsService) {
        return new JwtAuthenticationTokenFilter(
                this.jwtProperties,jwtTokenUtil,myUserDetailsService);
    }

}
