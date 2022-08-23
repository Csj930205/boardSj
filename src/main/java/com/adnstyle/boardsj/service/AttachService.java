package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.AttachDto;
import com.adnstyle.boardsj.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachService {
    private final AttachRepository attachRepository;

    /*
     * 파일 리스트 조회
     * */
    public List<AttachDto> attachList(int refKey) {
        return attachRepository.attachList(refKey);
    }

    /*
     * 파일 업로드
     * */
    public int attachInsert(List<AttachDto> attachList) {
        return attachRepository.attachInsert(attachList);
    }

    /*
     * 파일 수정
     * */
    public int updateAttach(int seq) {

        return attachRepository.updateAttach(seq);
    }

    /*
     * 파일 개별 조회
     * */
    public AttachDto attachListDetail(int refKey) {
        return attachRepository.attachListDetail(refKey);
    }
}
