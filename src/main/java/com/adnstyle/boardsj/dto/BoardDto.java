package com.adnstyle.boardsj.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BoardDto {
    private int seq;
    private int gNum;
    private int ordered;
    private int depth;
    private String title;
    private String contents;
    private LocalDateTime postTime;
    private String id;
}
