package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근 제어가 중요함!!
		// Access Control
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?=loginform");
			return;
		}

		String boardNo = request.getParameter("board");
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		System.out.println(boardNo);
		
		BoardVo vo = new BoardVo();
		vo.setNo(Integer.parseInt(boardNo));
		vo.setTitle(title);
		vo.setContents(contents);
		
		new BoardDao().update(vo);
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
