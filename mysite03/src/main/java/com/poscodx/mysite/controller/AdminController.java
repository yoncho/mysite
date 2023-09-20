package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Auth(Role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SiteService siteSerivce;

	@RequestMapping("")
	public String main(Model model) {
		SiteVo siteVo = siteSerivce.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String mainUpate(SiteVo vo) {
		siteSerivce.updateSite(vo);
		System.out.println(vo.getTitle());
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
