package com.adnstyle.boardsj.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachDto {
    private int seq;
    private int refKey;
    private String displayName;
    private String saveName;
    private int size;
    private LocalDateTime postTime;
}
