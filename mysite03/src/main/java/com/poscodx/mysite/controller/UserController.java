package com.poscodx.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userSerivce;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo vo) {
		userSerivce.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public String auth(HttpSession session,
			@RequestParam(value = "email", required = true, defaultValue = "") String email,
			@RequestParam(value = "password", required = true, defaultValue = "") String password, Model model) {
		UserVo authUser = userSerivce.getUser(email, password);
		if (authUser == null) {
			model.addAttribute("email", email);
			return "/user/login";
		}
		/* 인증 성공 */
		session.setAttribute("authUser", authUser);
		System.out.println("authUser Name : " + authUser.getName());
		System.out.println("authUser No : " + authUser.getNo());
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate(); // 기존에 있던 session id 날려버림.

		return "redirect:/";
	}

//	@Auth 직접 만들 annotation, 인증이 필요함을 뜻함
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}
		/////////////////////////////////////////////////////

		UserVo userVo = userSerivce.getUser(authUser.getNo());
		model.addAttribute("userVo", userVo);

		return "/user/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpSession session, UserVo userVo) {
		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}
		/////////////////////////////////////////////////////
		userVo.setNo(authUser.getNo());
		userSerivce.update(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
}
