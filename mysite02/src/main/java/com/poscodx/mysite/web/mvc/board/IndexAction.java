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
		int totalBoardCount = new BoardDao().findTotalCount();

		//paging
		int totalPageCount =(int) Math.ceil((double)totalBoardCount / (double)boardCountPerPage);
		PagingVo page = paging(currentPage, totalPageCount, pagePerStep);
		request.setAttribute("page", page);
		
		//boardPerPage (current)
		List<BoardVo> list = pageBoardList(currentPage);
		request.setAttribute("list", list);
	
		//auth
		request.setAttribute("isAuth", isAuth);
		
		WebUtil.forward("board/list", request, response);
	}

	public List<BoardVo> pageBoardList(int currentPage){
		return new BoardDao().findByRange(boardCountPerPage, currentPage);
	}
	
	public PagingVo paging(int currentPage, int totalPage, int pagePerStep) {
		PagingVo vo = new PagingVo(totalPage, currentPage, pagePerStep);
		System.out.println();
		return vo;
	}
}
