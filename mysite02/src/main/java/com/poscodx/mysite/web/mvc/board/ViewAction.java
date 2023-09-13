package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.utils.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근 제어가 중요함!!
		// Access Control
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boolean isAuthUser = false;
		boolean isWriter = false;
		
		if (authUser != null) {
			isAuthUser = true;
			if(authUser.getNo() == Integer.parseInt(request.getParameter("writer"))) {
				isWriter = true;
			}
			request.setAttribute("no", authUser.getNo());
		}
		
		String boardNo = request.getParameter("board");
		//update hit
		new BoardDao().updateHitByNo(Integer.parseInt(boardNo));
		
		//select Board 
		BoardVo board = new BoardDao().findByNo(Integer.parseInt(boardNo));
		request.setAttribute("board", board);
		request.setAttribute("isAuthUser", isAuthUser);
		request.setAttribute("isWriter", isWriter);
		
		WebUtil.forward("board/view", request, response);
	}
}
