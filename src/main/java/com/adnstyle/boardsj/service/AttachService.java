package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.AttachDto;
import com.adnstyle.boardsj.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachService {
    private final AttachRepository attachRepository;

    public int attachInsert(AttachDto attachDto){
        return attachRepository.attachInsert(attachDto);
    }
}
