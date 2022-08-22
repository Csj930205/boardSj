package com.adnstyle.boardsj.config.auth;

import com.adnstyle.boardsj.dto.Role;
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
    /*
        Spring Security
         - 스프링 기반 애플리케이션의 보안을 위한 표준 인증과 인가 기능을 가진 프레임워크.

        1) Spring Security 인증 진행 순서
            1. 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
            2. 로그인 진행이 완료되면 시큐리티 session을 만들어줍니다 (Security ContextHolder)
            3. 인증 오브젝트 Authentication 타입 객체안에 User(MemberDto) 정보가 포함되어야 한다.
            4. User(MemberDto) 오브젝트타입은 UserDetails 타입 객체로 커스텀을 하려면 상속받아 작성해야 한다.
            5. getAuthorities 메소드는 인증하는 User(MemberDto)가 어떤 종류의 권한을 설정.

        2) Spring Security 인증 시스템 작동 방식
            1. 시큐리티 설정에서 loginProcessingUrl 요청이 오게되면 자동으로 UserDetailsService의 loadUserByUsername 함수가 실행됨
            2. loadUserByUsername이 DB에 username이 있는지 검사
            3. username과 DB의 정보가 일치하게 되면 SecurityConfig에서 상속받은 configure(AuthenticationManagerBuilder) 함수에서
               패스워드 비교가 이루어진다.
            4. username과 password가 일치하게 되면 User(MemberDto)객체가 반환되며
               스프링 시큐리티 영역에 등록 된 후 defaultSuccessUrl로 이동하게 된다.
    */

    /*
        OAuth
         - 사용자 Resource를 관리하는 Service(Google 등)에서 제3의 애플리케이션에게 사용자의 패스워드 제공 없이
           인증, 인가할 수 있는 표준 프로토콜

        1) OAuth2 작동 방식
            1. Authorization code로 Google OAuth Server에 Token을 요청
            2. 로그인 할 때 외에 OAuth Server와 통신이 필요한 경우, 발급 받은 Token을 저장
            3. Access Token으로 이름, 이메일 등의 정보를 요청
            4. DB에 존재하지 않는 사용자의 경우 새로 등록.
            5. 기존 등록자의 경우 정보 업데이트 진행

            Spring Security에서 제공하는 Provider가 OAuth2 인증 단계에서 권한 서버와 토큰서버에 ID와
            Secret을 수동으로 HTTP API를 요청하는 코드를 구현하지 않아도 된다.

     */
    private final CustomOAuth2UserService customOAuth2UserService;
    private final MemberService memberService;


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
                .authorizeHttpRequests() //인가요청에 대한 처리구문
                .antMatchers("/member/**").hasRole("ADMIN") // member 폴더의 모든 파일은 ADMIN 권한을 인가받은 유저만 접근가능
                .antMatchers("/board/**").hasAnyRole("ADMIN", "USER", "GUEST")
                .antMatchers("/item/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/**").permitAll() // 인증받지 않은 유저까지 접근을 허용

            .and()
                .formLogin() //로그인 폼 처리
                .loginPage("/auth/login.do") // 로그인페이지로 이동
                .defaultSuccessUrl("/auth/login_access.do") // 인증성공 후 로그인이 성공하면 이동되는 기본 주소
                .failureUrl("/denied/denied.do") // 실패지 이동되는 주소
                .loginProcessingUrl("/login_proc") // 시큐리티가 해당 주소로 요청이 오는 로그인을 가로채서 요청을 처리해준다.(form method=post => post 처리방식과 동일 하다고 판단됨)
                .permitAll(); // 로그인페이지에 인증받지 않은 유저까지 접근을 허용

        http
            .sessionManagement()
                .maximumSessions(1) // 세선 최대 허용 수
                .maxSessionsPreventsLogin(false); // false의 경우 중복 로그인을 하게 되면 이전 로그인이 만료되어 로그아웃됨

        http
                .oauth2Login() // oauth2Login 요청 처리
                .defaultSuccessUrl("/") // form 로그인과 동일 기능
                .failureUrl("/denied/denied") // form 로그인과 동일 기능
                .userInfoEndpoint() // 로그인 성공 후 사용자의 정보를 가져온다.
                .userService(customOAuth2UserService); // 사용자의 정보를 처리 할 때 사용됨
        
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout.do")) //로그아웃 
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동 될 페이지 설정
                .invalidateHttpSession(true) // 로그인정보에 대한 session을 삭제
                    
            .and()
                .exceptionHandling()
                .accessDeniedPage("/auth/login.do")

            .and()
                .csrf().disable(); //csrf 토큰 비활성화(시큐리티의 경우 csrf토큰이 있어야 접근이 가능함으로 비활성화)
    }
    
    /*
    * 로그인 요청이 들어온 정보에 대해 검사(패스워드 비교)
    * */
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
