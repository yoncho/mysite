package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Controller
public class MainController {
	@Autowired
	private SiteService siteSerivce;
	
	@RequestMapping("/")
	public String main(Model model) {
		SiteVo siteVo = siteSerivce.getSite();
		System.out.println(siteVo);
		model.addAttribute("siteVo", siteVo);
		return "main/index";
		//prefix : /WEB-INF/views/
		//suffix : .jsp
	}
	
}
