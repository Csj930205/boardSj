package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.AttachDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AttachRepository {
    int attachInsert(AttachDto attachDto);

    int attachDelete(int seq);

    AttachDto attachDetail(int seq);

    List<AttachDto> attachList();
}
