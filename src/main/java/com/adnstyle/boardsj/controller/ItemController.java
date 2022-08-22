package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.ItemDto;
import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.dto.User;
import com.adnstyle.boardsj.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /*
     * 상품리스트 전체조회
     * */
    @GetMapping("itemList")
    public String listItem(Model model) {
        List<ItemDto> itemList = itemService.listItem();
        model.addAttribute("itemList", itemList);
        return "item/itemList";
    }

    /*
     * 상품 등록
     * */
    @PostMapping("insertItem")
    public String itemInsert(ItemDto itemDto, HttpSession session) {
        MemberDto memberInsertSession = (MemberDto) session.getAttribute("memberLoginInfo");
        User socialInsertSession = (User) session.getAttribute("user");

        if (memberInsertSession != null) {
            String itemMemberId = memberInsertSession.getId();
            itemDto.setMemberId(itemMemberId);
        } else {
            String itemUserId = socialInsertSession.getEmail();
            itemDto.setMemberId(itemUserId);
        }
        int result = itemService.itemInsert(itemDto);
        if (result > 0) {
            return "redirect:/item/itemList";
        } else {
            return "redirect:/item/itemInsert";
        }
    }

    /*
     * 상품 수정
     * */
    @PostMapping("itemUpdate")
    public String itemUpdate(ItemDto itemDto, HttpSession session) {
        MemberDto memberUpdateSession = (MemberDto) session.getAttribute("memberLoginInfo");
        User socialUpdateSession = (User) session.getAttribute("user");

        if (memberUpdateSession != null) {
            String itemMemberId = memberUpdateSession.getId();
            itemDto.setMemberId(itemMemberId);
        } else {
            String itemUserId = socialUpdateSession.getEmail();
            itemDto.setMemberId(itemUserId);
        }
        int result = itemService.itemUpdate(itemDto);
        if (result > 0) {
            return "redirect:/item/itemList";
        } else {
            return "redirect:/item/itemmodify/{seq}";
        }
    }

    /*
     * 상품 삭제
     * */
    @GetMapping("/delete/{seq}")
    public String deleteItem(@PathVariable("seq") int seq) {
        int result = itemService.deleteItem(seq);
        if (result > 0) {
            return "redirect:/item/itemList";
        } else {
            return "redirect:/item/itemmodify/{seq}";
        }
    }
}
