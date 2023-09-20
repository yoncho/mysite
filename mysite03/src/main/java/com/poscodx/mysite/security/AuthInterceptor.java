package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1. Handler Type Check 
		if (!(handler instanceof HandlerMethod)) {
			// Default Servlet Handler가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}
		
		//2. Handler Method
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler Method`s @Auth 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. @Auth not found
		if(auth == null) {
			return true;
		}
		
		//5. @Auth found, Authenfication Check
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//6. Auth. Ok!
		return true;
	}
	
}
