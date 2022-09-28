package com.ll.exam.damda;


import com.ll.exam.damda.service.user.OAuth2UserService;
import com.ll.exam.damda.service.user.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // db 정보
    @Autowired
    private DataSource dataSource;

    //userDetailsService를 상속받은 service
    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private OAuth2UserService oAuth2UserService;

    // JDBC 기반의 tokenRepository 구현체
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // csrf 토큰 사용 X (추후 수정 필요)
                .csrf(
                        csrf -> csrf
                                .disable()
                )

                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .antMatchers("/**").permitAll()
                )

                .headers(
                        headers -> headers
                                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                )

                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/user/login")
                                // 로그인 성공 시 호출되는 URL
                                .defaultSuccessUrl("/travel/design/plan/list")
                )

                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                // 로그아웃시 자동로그인 쿠키와 세션 제거
                                .deleteCookies("remember-me", "JSESSIONID")
                )

                // 자동로그인 구현을 위한 rememberMe 설정
                .rememberMe(
                        rememberMe -> rememberMe
                                .key("rem-me-key")
                                // userDetailsService를 구현한 service를 가져와야 함
                                .userDetailsService(userSecurityService)
                                // 로그인 html 페이지에서 이용할 id값
                                // 이 값은 default이며 변경할 시 로그인 페이지의 id 값도 수정
                                .rememberMeParameter("remember-me")
                                // 쿠키의 이름, f12 > application에서 확인 가능
                                .rememberMeCookieName("remember-login")
                                // 쿠키의 값을 저장할 DataSource
                                .tokenRepository(persistentTokenRepository())
                                // 쿠키의 유효기간, 7일을 계산함
                                .tokenValiditySeconds(7 * 24 * 60 * 60)
                )

                // 소셜로그인 구현을 위한 Oauth2 설정
                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .loginPage("/user/login")
                                // 로그인 성공 시 호출되는 URL
                                .defaultSuccessUrl("/travel/design/plan/list")
                                .userInfoEndpoint(
                                        userInfoEndpoint -> userInfoEndpoint
                                                .userService(oAuth2UserService)
                                )
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod(PUT);
        corsConfiguration.addAllowedMethod(GET);
        corsConfiguration.addAllowedMethod(POST);
        corsConfiguration.addAllowedMethod(DELETE);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }


}