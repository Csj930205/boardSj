package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.config.auth.SessionUser;
import com.adnstyle.boardsj.dto.BoardDto;
import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.dto.Role;
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

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

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
        MemberDto member=(MemberDto) session.getAttribute("memberLoginInfo");
        String writer = member.getId();
        String name = member.getName();
        boardDto.setId(writer);
        boardDto.setName(name);
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
//      일반회원 및 소셜회원 세션정보를 가져옴
        MemberDto memberId = (MemberDto) session.getAttribute("memberLoginInfo");
        SessionUser user = (SessionUser) session.getAttribute("user");

//      일반회원 세션값이 존재하지 않을 경우 소셜회원 세션을 이용하여 게시판에 값을 입력해준다.
        if(memberId!=null){
            String replayWriter = memberId.getId();
            String replayName = memberId.getName();
            boardDto.setId(replayWriter);
            boardDto.setName(replayName);
        }else{
            String userId = user.getEmail();
            String userName = user.getName();
            boardDto.setId(userId);
            boardDto.setName(userName);
        }
//      답글 등록 전 게시물 순서를 위해 업데이트 이후 등록
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
