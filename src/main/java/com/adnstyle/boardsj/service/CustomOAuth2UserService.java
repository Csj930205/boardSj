package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.config.auth.SessionUser;
import com.adnstyle.boardsj.dto.OAuthAttributes;
import com.adnstyle.boardsj.dto.User;
import com.adnstyle.boardsj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /*
     * 소셜 전체리스트 조회
     * */
    public List<User> listUser(){
        return userRepository.listUser();
    }

    /*
    * OAuth2 Google 로그인 처리
    * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//        현재 로그인 진행 중인 서비스를 구분 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

//        oauth2 로그인 진행 시 키가 되는 필드값
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

//     OAuthAttributes: attribute를 담을 클래스 (개발자가 생성)
        OAuthAttributes attribues = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attribues);

//        SessionUser : 세션에 사용자 정보를 저장하기 위한 DTO 클래스(개발자가 생성)
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attribues.getAttributes(),
                attribues.getNameAttributeKey()
        );
    }

    /*
    * OAuth2 Google 가입처리
    * */
    private User saveOrUpdate(OAuthAttributes attributes){
        User user;
        if(userRepository.findByEmail(attributes.getEmail())!=null){
            user = userRepository.findByEmail(attributes.getEmail());
        }else {
            user = attributes.toEntity();
            userRepository.save(user);
            user=userRepository.findByEmail(attributes.getEmail());
        }
        return user;
    }

}
