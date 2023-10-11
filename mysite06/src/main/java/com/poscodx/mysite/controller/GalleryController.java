package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.GalleryService;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index() {
		return "gallery/index";
	}
	
	@Auth(Role="ADMIN")// add/ remove의 경우! 사용해야함..
	public void add() {
		
	}
	
}
