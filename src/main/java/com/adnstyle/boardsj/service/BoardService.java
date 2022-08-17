package com.adnstyle.boardsj.service;

import com.adnstyle.boardsj.dto.BoardDto;
import com.adnstyle.boardsj.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /*
    * 게시글 전체 조회
    * */
    public List<BoardDto> listBoard(){
        return boardRepository.listBoard();
    }

    /*
    * 게시글 등록
    * */
    public int insertBoard(BoardDto boardDto){
        return boardRepository.insertBoard(boardDto);
    }

    /*
    * 게시글 개별 조회
    * */
    public BoardDto detailListBoard(int seq){
        return boardRepository.detailListBoard(seq);
    }

    /*
     * 게시글 삭제
     * */
    public int deleteBoard(int seq){
        return boardRepository.deleteBoard(seq);
    }

    /*
    * 답변을 위한 게시글 상세조회
    * */
    public BoardDto replayGetSeq(int seq){
        return boardRepository.replayGetSeq(seq);
    }

    /*
     * 답글 등록
     * */
    public int insertBoardReplay(BoardDto boardDto){
        return boardRepository.insertBoardReplay(boardDto);
    }

    /*
    * 답글 정렬
    * */
    public int updateReplayBoard(BoardDto boardDto){
        return boardRepository.updateReplayBoard(boardDto);
    }


}
