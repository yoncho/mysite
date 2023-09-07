package com.poscodx.mysite.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;

public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("a");
		
		if("insert".equals(action)) {
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
			response.sendRedirect("/mysite02/guestbook");
		}else if ("delete".equals(action)) {
			//get No & Password
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			
			//delete
			new GuestbookDao().delete(Integer.parseInt(no), password);
			response.sendRedirect("/mysite02/guestbook");
		}else if ("deleteForm".equals(action)) {
			String no = request.getParameter("no");
			request.setAttribute("no", no);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);
		}else {
			List<GuestbookVo> list = new GuestbookDao().findAll();
			request.setAttribute("list", list); //request에 전달할 data를 setting!!
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/index.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
