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

public class IndexAction implements Action {

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
		
		request.setAttribute("isAuth", isAuth);
		
		List<BoardVo> list = new BoardDao().findAll();
		request.setAttribute("list", list);
		WebUtil.forward("board/list", request, response);
	}

}
