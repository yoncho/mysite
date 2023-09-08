package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.vo.UserVo;
import com.poscodx.mysite.web.mvc.utils.WebUtil;
import com.poscodx.web.mvc.Action;

public class UpdateformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//접근 제어가 중요함!!
		// Access Control 
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user?=loginform");
			return;
		}
		
		////////////////////////////////// 접근제어로 위 코드가 자주 사용됨.
		
		WebUtil.forward("user/updateform", request, response);
	}

}
