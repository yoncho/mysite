package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PagingVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.utils.WebUtil;

public class IndexAction implements Action {
	private int boardCountPerPage = 8;
	private int pagePerStep = 5;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//접근 제어가 중요함!!
		// Access Control 
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boolean isAuth = false;
		if(authUser != null) {
			isAuth = true;
			request.setAttribute("no", authUser.getNo());
		}
		
		String stringCurrentPage = (String)request.getParameter("page");
		int currentPage = stringCurrentPage == null ? 1 : Integer.parseInt(stringCurrentPage);
		int totalBoardCount = 0;
		List<BoardVo> list = new ArrayList();
		
		String keyword = request.getParameter("kwd");
		if(keyword == null || keyword.isEmpty()) {
			totalBoardCount = new BoardDao().findTotalCount();
			list = pageBoardList(currentPage);
		}else {
			totalBoardCount = new BoardDao().findTotalCountByKeyword(keyword);
			list = pageBoardListByKeyword(currentPage, keyword);
		}
		
		//paging
		/*
		 * totalBoardCount : 총 작성된 글의 수
		 * currentPage : 현재 page 번호
		 * pagePerStep : 한번에 몇개의 page를 보여줄 것인지 (ex: 1 2 3 4 5 > 이렇게면 5개)
		 * boardCountPerPage : 한 page에 표시할 글의 수
		 * */
		PagingVo page = paging(totalBoardCount, currentPage, pagePerStep, boardCountPerPage);
		request.setAttribute("page", page);

		//boardPerPage (current)
		request.setAttribute("list", list);
		
		//auth
		request.setAttribute("isAuth", isAuth);
		
		WebUtil.forward("board/list", request, response);
	}

	public List<BoardVo> pageBoardList(int currentPage){
		return new BoardDao().findByRange(boardCountPerPage, currentPage);
	}
	
	public List<BoardVo> pageBoardListByKeyword(int currentPage, String keyword){
		return new BoardDao().findByRangeAndKeyword(boardCountPerPage, currentPage, keyword);
	}
	
	public PagingVo paging(int totalBoardCount,int currentPage,int pagePerStep,int boardCountPerPage) {
		PagingVo vo = new PagingVo(totalBoardCount, currentPage, pagePerStep, boardCountPerPage);
		return vo;
	}
}
