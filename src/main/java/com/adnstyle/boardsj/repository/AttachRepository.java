package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.AttachDto;
import com.adnstyle.boardsj.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AttachRepository {

    /*
    * 파일 리스트 조회
    * */
    List<AttachDto> attachList(int refKey);

    /*
    * 파일 업로드
    * */
    int attachInsert(List<AttachDto> attachList);

    /*
    * 파일 삭제
    * */
    int attachDelete(int seq);

    /*
    * 파일 개별 조회
    * */
    AttachDto attachListDetail(int refKey);
}
