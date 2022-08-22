package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.ItemDto;
import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.dto.User;
import com.adnstyle.boardsj.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService{
    private final ItemRepository itemRepository;

    /*
    * 상품리스트 전체조회
    * */
    public List<ItemDto> listItem(){
        return itemRepository.listItem();
    }

    /*
    * 상품 개별조회
    * */
    public ItemDto getList(int seq){
        return itemRepository.getList(seq);
    }
    /*
    * 상품 등록
    * */
    public int itemInsert(ItemDto itemDto){
        return itemRepository.itemInsert(itemDto);
    }

    /*
    * 상품 수정
    * */
    public int itemUpdate(ItemDto itemDto){
        return itemRepository.itemUpdate(itemDto);
    }

    /*
    * 상품 삭제
    * */
    public int deleteItem(int seq){
        return itemRepository.deleteItem(seq);
    }
}
