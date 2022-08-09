package com.adnstyle.boardsj.config;

import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final MemberService memberService;

    /*
    * 비밀번호 암호화
    * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    * 요청이 들어오는 경우 권한 설정 & 로그인 & 로그아웃
    * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/member/**").authenticated()
                .antMatchers("/board/**").authenticated()
                .antMatchers("/**").permitAll();
        http.formLogin()
                .loginPage("/auth/login.do")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .passwordParameter("pw")
                .permitAll();
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout.do"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
        http.exceptionHandling()
                .accessDeniedPage("/");
        return http.build();
    }

    /*
    * static 디렉토리의 하위 파일 목록은 인증을 무시함.
    * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "img/**", "/lib/**");
    }
}
