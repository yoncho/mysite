package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String no = request.getParameter("userNo");
		String gNo = request.getParameter("gNo");
		String oNo = request.getParameter("oNo");
		String depth = request.getParameter("depth");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(Integer.parseInt(no));
		if(gNo == null) {
			vo.setGno(new BoardDao().findLastNoOfGroup() + 1);
			vo.setOno(1);
			vo.setDepth(1);
		}else {
			vo.setGno(Integer.parseInt(gNo));
			vo.setOno(2);
			vo.setDepth(2);
		}
		//insert
		new BoardDao().insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
