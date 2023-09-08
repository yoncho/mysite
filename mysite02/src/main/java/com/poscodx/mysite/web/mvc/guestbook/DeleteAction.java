package com.poscodx.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get No & Password
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		//delete
		new GuestbookDao().delete(Integer.parseInt(no), password);
		response.sendRedirect(request.getContextPath()+"/guestbook");
	}

}
