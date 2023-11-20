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
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->{
            csrf.disable();
        });
        http.authorizeHttpRequests((auth)->{
            auth.requestMatchers("/**","/brand/**").permitAll(); //모든사용자 접근 권한
            // auth.requestMatchers("/","/member/**","/brand").hasRole("USER"); //사용자만 접근 권한
            // auth.requestMatchers().hasAnyRole("CUSTOMER","ADMIN"); //고객사,관리자 접근 권한
            //auth.requestMatchers().hasRole("ADMIN"); //고객사, 접근 권한
        });
        http.formLogin((login)->{
            login.loginPage("/user/login");
            login.defaultSuccessUrl("/");
            login.usernameParameter("email");
            login.failureUrl("/user/login/error");
        });
        http.logout((logout)->{
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"));
            logout.logoutSuccessUrl("/");
        });
        return http.build();
    }
}
