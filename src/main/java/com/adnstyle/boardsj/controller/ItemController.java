package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.ItemDto;
import com.adnstyle.boardsj.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /*
    * 아이템 리스트 조회
    * */
    @GetMapping("itemList")
    public String listItem(Model model){
        List<ItemDto> itemList = itemService.listItem();
        model.addAttribute("itemList",itemList);
        return "item/itemList";
    }
}
