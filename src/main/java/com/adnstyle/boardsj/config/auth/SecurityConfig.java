package com.adnstyle.boardsj.config.auth;

import com.adnstyle.boardsj.service.CustomOAuth2UserService;
import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private final MemberService memberService;
    private final CustomOAuth2UserService customOAuth2UserService;

    /*
     * static 디렉토리의 하위 파일 목록은 인증을 무시함.
     * */
        public void configure(WebSecurity web){
         web.ignoring().antMatchers("/css/**", "/js/**", "img/**", "/lib/**", "/signup/**", "/denied/**");
        }

    /*
    * 비밀번호 암호화
    **/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    * 요청이 들어오는 경우 권한 설정 & 로그인 & 로그아웃
    * */
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .antMatchers("/member/**").authenticated()
                .antMatchers("/board/**").authenticated()
                .antMatchers("/**").permitAll()
            .and()
                .formLogin()
                .loginPage("/auth/login.do")
                .defaultSuccessUrl("/auth/login_access.do")
                .failureUrl("/denied/denied.do")
                .loginProcessingUrl("/login_proc")
                .permitAll()
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
            http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout.do"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            .and()
                .exceptionHandling()
                .accessDeniedPage("/auth/login.do")
            .and()
                .csrf().disable();
    }
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
