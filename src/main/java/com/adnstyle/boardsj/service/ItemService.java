package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.ItemDto;
import com.adnstyle.boardsj.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
