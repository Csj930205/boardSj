package com.adnstyle.boardsj.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

/*
* 소셜 사용자의 정보를 담을 클래스
* */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name, String email, String picture){
        this.attributes =attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if(registrationId.equals("google")){
            return ofGoogle(userNameAttributeName, attributes);
        }else{
            return ofKakao(userNameAttributeName, attributes);
        }
    }

    /*
    * google
    * */
    public static OAuthAttributes ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /*
    * kakao
    * */
    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){
//        kakao는 kakao_account에 유저 정보가 존재(email)
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
//        kakak_account 안에 profile이라는 JSON객체가 존재(nickname, profile_image 등)
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .picture((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}

