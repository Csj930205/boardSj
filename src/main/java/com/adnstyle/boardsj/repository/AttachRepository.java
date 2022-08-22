package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.AttachDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AttachRepository {

    /*
    * 파일 리스트 조회
    * */
    List<AttachDto> attachList();
    /*
    * 파일 업로드
    * */

    int attachInsert(AttachDto attachDto);

    int attachDelete(int seq);

    AttachDto attachDetail(int seq);
}
