package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.BoardDto;
import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final DomainController domainController;

    /*
    * 게시판 글 조회 처리
    * */
    @GetMapping("list.do")
    public String listBoard(Model model){
        List<BoardDto> boardList = boardService.listBoard();
        model.addAttribute("boardList",boardList);
        return "board/list";
    }

    /*
    * 게시글 등록 처리
    * */
    @PostMapping("insertBoard.do")
    public String insertBoard(BoardDto boardDto, HttpSession session){
        int result = 0;
        MemberDto memberId=(MemberDto) session.getAttribute("loginDto");
        String writer = memberId.getId();
        boardDto.setId(writer);
        result += boardService.insertBoard(boardDto);
        if(result>0){
            return "redirect:/board/list.do";
        }else {
            return "redirect:/board/post.do";
        }
    }

    /*
     * 게시글 삭제
     * */
    @GetMapping("/delete/{seq}")
    public String deleteBoard(@PathVariable("seq") int seq) {
        int result = boardService.deleteBoard(seq);
        if (result > 0) {
            return "redirect:/board/list.do";
        } else {
            return "redirect:/board/delete/{seq}";
        }
    }

    /*
     * 답글 등록
     * */
    @PostMapping("insertBoardReplay.do")
    public String insertBoardReplay(BoardDto boardDto, HttpSession session){
        MemberDto memberId = (MemberDto) session.getAttribute("loginDto");
        String replayWriter = memberId.getId();
        boardDto.setId(replayWriter);
        int insertResult =0;
        insertResult += boardService.updateReplayBoard(boardDto);
        insertResult += boardService.insertBoardReplay(boardDto);
        if(insertResult>0){
            return "redirect:/board/list.do";
        }else{
            return "redirect:/board/replay/{seq}";
        }
    }
}
