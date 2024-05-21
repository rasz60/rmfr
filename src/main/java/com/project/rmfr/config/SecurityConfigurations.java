package com.project.rmfr.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        http.csrf().disable();
        http.cors().disable();

        http
                .headers().frameOptions().disable() // h2-console 화면 사용을 위한 옵션
                .and()
                // 권한 설정
                .authorizeRequests() //권한 부여를 위한 메서드
                .antMatchers( "/"
                        ,"/api/**"
                        ,"/signup"
                        ,"/signup/**"
                        ,"/formLogin"
                        ,"/loginProc"
                        ,"/oauth2/**"
                        ,"/error"
                        ,"/login"
                        ,"/login/**"
                        ,"/vue/css/**"
                        ,"/vue/images/**"
                        ,"/vue/js/**"
                        ,"/vue/h2-console/**"
                        ,"/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                // form 로그인 설정
                .formLogin() //Security가 지원하는 폼 형식 로그인 방식
                .loginPage("/formLogin") // 로그인 페이지
                .usernameParameter("userName")
                .passwordParameter("password")
                .loginProcessingUrl("/loginProc") // 로그인 정보를 해당 URL로 전달하면 Security가 자동 처리
                .defaultSuccessUrl("/loginSucc") // 로그인 완료 후 리턴 URL
                .and()
                //로그아웃 설정
                .logout()
                .logoutSuccessUrl("/")
        ;
        */

        // security v6.1.0부터 람다식 함수형으로 설정
        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .cors((corsConfig) ->
                        corsConfig.disable()
                )

                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )

                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                //.requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/", "/member/login/**", "/member/loginProc/**", "/member/signup/**", "/api/**").permitAll()
                                .anyRequest().permitAll()
                )

                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/")
                                .usernameParameter("mId")
                                .passwordParameter("mPw")
                                .loginProcessingUrl("/member/loginProc")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/?loginError=true")
                )

                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")
                );

        return http.build();
    }
}
