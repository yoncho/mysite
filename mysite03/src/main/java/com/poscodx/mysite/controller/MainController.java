package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private SiteService siteSerivce;
	
	@RequestMapping("/")
	public String main(@AuthUser UserVo userVo, Model model) {
		SiteVo siteVo = siteSerivce.getSite();
		boolean isAdmin = false;
		if(userVo != null) {
			System.out.println("null profile");
			isAdmin =  userVo.getRole().equals("ADMIN");
		}
		
		if(siteVo.getProfile().isEmpty()) {
			siteVo.setProfile("/assets/images/tm.png");
		}
		
		model.addAttribute("siteVo", siteVo);
		model.addAttribute("isAdmin", isAdmin);
		return "main/index";
		//prefix : /WEB-INF/views/
		//suffix : .jsp
	}
	
}
