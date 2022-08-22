package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.AttachDto;
import com.adnstyle.boardsj.dto.ItemDto;
import com.adnstyle.boardsj.repository.AttachRepository;
import com.adnstyle.boardsj.repository.ItemRepository;
import com.adnstyle.boardsj.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachService {
    private final AttachRepository attachRepository;

    /*
    * 파일 리스트 조회
    * */
    public List<AttachDto> attachList(){
        return attachRepository.attachList();
    }

    /*
    * 파일 업로드
    * */
    public int attachInsert(AttachDto attachDto){
        return attachRepository.attachInsert(attachDto);
    }
}
