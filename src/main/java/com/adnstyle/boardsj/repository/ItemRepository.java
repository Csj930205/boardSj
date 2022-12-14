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

    /*
    * 상품 개별 조회
    * */
    ItemDto getList(int seq);

    /*
    * 상품 등록
    * */
    int itemInsert(ItemDto itemDto);

    /*
    * 상품 수정
    * */
    int itemUpdate(ItemDto itemDto);

    /*
    * 상품 삭제
    * */
    int deleteItem(int seq);
}
