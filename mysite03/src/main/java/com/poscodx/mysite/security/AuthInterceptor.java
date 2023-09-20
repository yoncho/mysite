package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.controller.AdminController;
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
		
		//3-1. Handler Method`s @Auth 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//3-2. Handler Type`s @Auth가 없는 경우, TYPE(Class)에 붙어있는지 확인
		if(auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
			
		}
		
		//4. @Auth not found
		if(auth == null) {
			return true;
		}
		System.out.println(auth.Role());
		//5. @Auth found, Authenfication Check
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}

		//6. 권한(Authorization)체크 @Auth의 Role! (USER/ADMIN)
		String role = auth.Role();
		String authUserRole = authUser.getRole();
		if(!role.equals(authUserRole))
		{
			response.sendRedirect(request.getContextPath());
			return false;
		}
		System.out.println("인증된 admin입니다.");
		//6. Auth. Ok!
		return true;
	}
	
}
