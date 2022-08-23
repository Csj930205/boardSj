package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.AttachDto;
import com.adnstyle.boardsj.dto.ItemDto;
import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.dto.User;
import com.adnstyle.boardsj.repository.AttachRepository;
import com.adnstyle.boardsj.repository.ItemRepository;
import com.adnstyle.boardsj.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService{
    private final ItemRepository itemRepository;
    private final HttpSession session;
    private final FileUtil fileUtil;
    private final AttachRepository attachRepository;

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
    * Attach 개별 조회
    * */
    public AttachDto attachListDetail(int refKey) {
        return attachRepository.attachListDetail(refKey);
    }

    /*
    * 상품 등록(파일 첨부 미포함)
    * */
    public int itemInsert(ItemDto itemDto) {
        MemberDto memberInsertSession = (MemberDto) session.getAttribute("memberLoginInfo");
        User socialInsertSession = (User) session.getAttribute("user");
        if (memberInsertSession != null) {
            String itemMemberId = memberInsertSession.getId();
            itemDto.setMemberId(itemMemberId);
        } else {
            String itemUserId = socialInsertSession.getEmail();
            itemDto.setMemberId(itemUserId);
        }
        return itemRepository.itemInsert(itemDto);
    }

    /*
    * 상품 등록(Attach 포함)
    * */
    public int itemInsert(ItemDto itemDto, MultipartFile[] files) {
        int result = 1;
        if (itemInsert(itemDto) == 0){
           return  0;
        }
        List<AttachDto> fileList = fileUtil.uploadFiles(files, itemDto.getSeq());
        if (CollectionUtils.isEmpty(fileList) == false) {
            result = attachRepository.attachInsert(fileList);
            if (result < 1) {
                result = 0;
            }
        }
        return result;
    }

    /*
    * 상품 수정
    * */
    public int itemUpdate(ItemDto itemDto){
        MemberDto memberUpdateSession = (MemberDto) session.getAttribute("memberLoginInfo");
        User socialUpdateSession = (User) session.getAttribute("user");
        if (memberUpdateSession != null) {
            String itemMemberId = memberUpdateSession.getId();
            itemDto.setMemberId(itemMemberId);
        } else {
            String itemUserId = socialUpdateSession.getEmail();
            itemDto.setMemberId(itemUserId);
        }
        return itemRepository.itemUpdate(itemDto);
    }

    /*
    * 상품 삭제
    * */
    public int deleteItem(int seq){
        return itemRepository.deleteItem(seq);
    }
}
