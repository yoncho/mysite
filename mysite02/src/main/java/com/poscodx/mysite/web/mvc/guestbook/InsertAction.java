package com.poscodx.mysite.web.mvc.guestbook;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;
import com.poscodx.web.mvc.Action;

public class InsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get request
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String contents = request.getParameter("content");
		
		//date
		java.util.Date utilDate = new java.util.Date();
		long currentMilliseconds = utilDate.getTime();
		Date date = new Date(currentMilliseconds);
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setContents(contents);
		vo.setRegDate(date);
		
		//insert
		new GuestbookDao().insert(vo);
		
		//redirection
		response.sendRedirect(request.getContextPath()+"/guestbook");
	}

}
