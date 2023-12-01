package com.comeandsee.brandscan.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Log4j2
@EnableWebSecurity
public class SecurityConfig {
    //password 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf 비활성화
        http.csrf((csrf)->{
            csrf.disable();
        });

        // 접근 권한
        http.authorizeHttpRequests((auth)->{
            //auth.requestMatchers("/brand/request").hasRole("USER"); // 사용자 접근 권한
            auth.requestMatchers("/brand/request", "/user/my_page","/brand/retouch").hasRole("USER"); // 사용자 접근 권한
            auth.requestMatchers("/management/**").hasRole("ADMIN"); // 관리자 접근 권한
            auth.anyRequest().permitAll();
        });

        // 커스텀 로그인
        http.formLogin((login)->{
            login.loginPage("/user/login");
            login.defaultSuccessUrl("/");
            login.usernameParameter("email");
            login.passwordParameter("password");
            //login.failureUrl("/error");
        });

        // 커스텀 로그아웃
        http.logout((logout)->{
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"));
            logout.logoutSuccessUrl("/");
        });

        return http.build();
    }
}
