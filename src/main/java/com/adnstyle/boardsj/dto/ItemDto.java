package com.adnstyle.boardsj.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDto {
    private int seq;
    private String name;
    private String title;
    private int price;
    private String color;
    private String mainImg;
    private String memberId;
    private LocalDateTime postTime;
    private int state;
    private int categoryNum;
}
