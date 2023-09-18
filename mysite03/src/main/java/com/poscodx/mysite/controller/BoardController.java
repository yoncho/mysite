package com.poscodx.mysite.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PagingVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private int boardCountPerPage = 8;
	private int pagePerStep = 5;
	
	@Autowired
	private BoardService boardService;

	@RequestMapping({"", "/", "/main"})
	public String main(HttpSession session, String page, String kwd, Model model) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boolean isAuth = false;

		if (authUser != null) {
			isAuth = true;
			model.addAttribute("userNo", authUser.getNo());
		}
		/////////////////////////////////////////////////////
		String stringCurrentPage = page;
		List<BoardVo> list = new ArrayList();
		int currentPage = stringCurrentPage == null ? 1 : Integer.parseInt(stringCurrentPage);
		int totalBoardCount = 0;
		
		totalBoardCount = boardService.findTotalCount(kwd);
		list = pageBoardList(currentPage, kwd);
		
		PagingVo paging = paging(totalBoardCount, currentPage, pagePerStep, boardCountPerPage);
		model.addAttribute("page", paging);
		model.addAttribute("list", list);
		model.addAttribute("isAuth", isAuth);
		return "board/list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpSession session, BoardVo vo, String pboard) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}
		/////////////////////////////////////////////////////
		vo.setUserNo(authUser.getNo());
		System.out.println(pboard);
		if (pboard == "") {
			vo.setGno(boardService.findLastNoOfGroup() + 1);
			vo.setOno(1);
			vo.setDepth(1);
		} else {
			BoardVo pVo = boardService.findByNo(Integer.parseInt(pboard));
			vo.setGno(pVo.getGno());
			vo.setOno(pVo.getOno() + 1);
			vo.setDepth(pVo.getDepth() + 1);
		}
		vo.setState("active");

		boardService.insert(vo);
		return "redirect:/board";
	}

	@RequestMapping(value = "/view/{boardNo}")
	public String view(HttpSession session, @PathVariable("boardNo") int boardNo, Model model) {
		System.out.println("boardNo : " + boardNo);
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		BoardVo board = boardService.findByNo(boardNo);
		boardService.updateHitByNo(boardNo);
		boolean isAuth = false;
		boolean isWriter = false;

		if (authUser != null) {
			isAuth = true;
			if (authUser.getNo() == board.getUserNo()) {
				isWriter = true;
			}
			model.addAttribute("no", authUser.getNo());
		}

		model.addAttribute("board", board);
		model.addAttribute("isAuth", isAuth);
		model.addAttribute("isWriter", isWriter);
		/////////////////////////////////////////////////////
		return "board/view";
	}

	@RequestMapping("/delete")
	public String delete(HttpSession session, @RequestParam("board") int boardNo) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/board";
		}
		/////////////////////////////////////////////////////

		boardService.updateStateByNo(boardNo);
		return "redirect:/board";
	}

	@RequestMapping(value = "/modify/{boardNo}", method = RequestMethod.GET)
	public String modify(HttpSession session, @PathVariable("boardNo") int boardNo, Model model) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		BoardVo board = boardService.findByNo(boardNo);
		if (authUser == null || board.getUserNo() != authUser.getNo()) {
			return "redirect:/user/board";
		}
		/////////////////////////////////////////////////////

		System.out.println("boardNo : " + boardNo);

		board.setUserNo(authUser.getNo());
		model.addAttribute("board", board);

		return "board/modify";
	}

	@RequestMapping(value = "/modify/{boardNo}", method = RequestMethod.POST)
	public String modify(HttpSession session, @PathVariable("boardNo") int boardNo, BoardVo boardVo) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/board/view/" + boardNo;
		}
		/////////////////////////////////////////////////////
		boardVo.setNo(boardNo);
		boardService.update(boardVo);
		return "redirect:/board/view/" + boardNo;
	}
	
	public List<BoardVo> pageBoardList(int currentPage, String keyword){
		return boardService.findByRange(boardCountPerPage, currentPage, keyword);
	}
	
	public PagingVo paging(int totalBoardCount,int currentPage,int pagePerStep,int boardCountPerPage) {
		PagingVo vo = new PagingVo(totalBoardCount, currentPage, pagePerStep, boardCountPerPage);
		return vo;
	}
}
