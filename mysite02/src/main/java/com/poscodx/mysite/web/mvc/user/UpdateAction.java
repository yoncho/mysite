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

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");

		UserVo userVo = new UserVo();
		userVo.setName(name);
		userVo.setEmail(email);
		userVo.setGender(gender);
		userVo.setPassword(password);
		
		//update 성공시 session 업데이트 (단, password가 빈칸이면 안됨)
		if(!"".equals(password) && new UserDao().update(userVo)) {
			UserVo updateUserVo = new UserDao().findByEmailAndPassword(email, password);
			//session의 정보 업데이트!
			System.out.println("update is ok");
			HttpSession session = request.getSession();
			session.setAttribute("authUser",updateUserVo);
			WebUtil.forward("user/updateform", request, response);
			return;
		}
		
		// redirect
		response.sendRedirect(request.getContextPath() + "/user?a=updateform");
	}

}
