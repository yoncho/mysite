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
		// Access Control
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?=loginform");
			return;
		}
		////////////////////////////////// 접근제어로 위 코드가 자주 사용됨. Filter로 넘기는게 좋음.. 

		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");

		UserVo userVo = new UserVo();
		userVo.setName(name);
		userVo.setEmail(email);
		userVo.setGender(gender);
		userVo.setPassword(password);

		new UserDao().update(userVo);
		authUser.setName(name);

		response.sendRedirect(request.getContextPath() + "/user?a=updateform");
	}

}
