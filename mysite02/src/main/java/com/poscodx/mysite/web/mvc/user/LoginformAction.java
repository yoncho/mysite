package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.utils.WebUtil;

public class LoginformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebUtil.forward("user/loginform", request, response);
	}
}
