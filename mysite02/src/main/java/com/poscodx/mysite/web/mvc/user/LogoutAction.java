package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.web.mvc.Action;

public class LogoutAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	 	// 사용자 정보 제거
		HttpSession session = request.getSession();
		session.removeAttribute("authUser");
		
		session.invalidate(); //세션을 유효하지 않게 만듬
		
		response.sendRedirect(request.getContextPath());
		
	}

}
