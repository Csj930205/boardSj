package com.adnstyle.boardsj.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");
    private final String key;
    private final String title;

    public String getKey(){
        return key;
    }
    public String getTitle(){
        return title;
    }

}
