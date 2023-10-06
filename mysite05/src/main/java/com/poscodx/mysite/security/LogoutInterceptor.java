package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.vo.SiteVo;

public class LogoutInterceptor implements HandlerInterceptor {
	@Autowired
	private ApplicationContext applicaionContext;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("authUser");
		session.invalidate();
		
		SiteVo site = (SiteVo)applicaionContext.getBean(SiteVo.class);
		site.setAdmin(false);
		response.sendRedirect(request.getContextPath());
		return false;
	}
	
}
