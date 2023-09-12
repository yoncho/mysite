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
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String no = request.getParameter("userNo");
		String pboard = request.getParameter("pboard");
		
		System.out.println("====================================");
		System.out.println("부모 보드 : " + pboard);
		System.out.println("제목 : " + title);
		System.out.println("사용자 PK : " + no);
		System.out.println("====================================");

		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(Integer.parseInt(no));
		if(pboard == null || pboard =="") {
			vo.setGno(new BoardDao().findLastNoOfGroup() + 1);
			vo.setOno(1);
			vo.setDepth(1);
		}else {
			BoardVo pVo = new BoardDao().findByNo(Integer.parseInt(pboard));
			vo.setGno(pVo.getGno());
			vo.setOno(pVo.getOno()+1);
			vo.setDepth(pVo.getDepth() + 1);
		}
		//insert
		new BoardDao().insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
