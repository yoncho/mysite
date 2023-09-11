package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.utils.WebUtil;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo = new UserDao().findByEmailAndPassword(email, password);
		
		//로그인 실패
		if(userVo == null) {
			request.setAttribute("email", email);
			WebUtil.forward("user/loginform", request, response);
			return;
		}
		
		//로그인 성공....!!
		
		//userVo를 저장해놓음. session (같은 jsessionid 의 요청일 경우 같은 session을 사용(공유)하기 위함)
		HttpSession session = request.getSession(true); // true를 하게 되면 manager한테 request session id를 달라 없으면 만들어서 달라	
		session.setAttribute("authUser",userVo);
		
		// redirect
		response.sendRedirect(request.getContextPath());
		
	}

}
