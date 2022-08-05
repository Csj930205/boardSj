package com.adnstyle.boardsj.repository;

import com.adnstyle.boardsj.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {
    /*
    * 게시글 전체조회
    * */
    List<BoardDto> listBoard();
    
    /*
    * 게시글 등록
    * */
    int insertBoard(BoardDto boardDto);

    /*
    * 게시글 삭제
    * */
    int deleteBoard(int seq);

    /*
    * 답글을 위한 게시글 상세 조회
    * */
    BoardDto replayGetSeq(int seq);

    /*
    * 답글 등록
    * */
    BoardDto insertBoardReplay(BoardDto boardDto);
}
