package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.ItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemRepository {

    /*
    * 상품리스트 전체조회
    * */
    List<ItemDto> listItem();
}
