package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Auth(Role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteSerivce;

	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo site = (SiteVo)applicationContext.getBean("site");
		model.addAttribute("siteVo", site);
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String mainUpate(
			SiteVo vo, 
			@RequestParam("file") MultipartFile file ) {
		String url = fileUploadService.restore(file);
		vo.setProfile(url);
		
		siteSerivce.updateSite(vo);
		
		//applicationContext에 있는 siteVo도 업데이트..!!
		SiteVo site = (SiteVo)applicationContext.getBean("site");
		site.setTitle(vo.getTitle());
		site.setProfile(vo.getProfile());
		site.setWelcome(vo.getWelcome());
		site.setDescription(vo.getDescription());
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
