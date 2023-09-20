package com.poscodx.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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

//	@RequestMapping(value = "/auth", method = RequestMethod.POST)
//	public String auth(HttpSession session,
//			@RequestParam(value = "email", required = true, defaultValue = "") String email,
//			@RequestParam(value = "password", required = true, defaultValue = "") String password, Model model) {
//		
//		UserVo authUser = userSerivce.getUser(email, password);
//		if (authUser == null) {
//			model.addAttribute("email", email);
//			return "/user/login";
//		}
//		/* 인증 성공 */
//		session.setAttribute("authUser", authUser);
//		System.out.println("authUser Name : " + authUser.getName());
//		System.out.println("authUser No : " + authUser.getNo());
//		return "redirect:/";
//	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate(); // 기존에 있던 session id 날려버림.
//
//		return "redirect:/";
//	}

//	@Auth 직접 만들 annotation, 인증이 필요함을 뜻함
	
	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpSession session, Model model) {
//		// Access Control : 접근 제어 (*횡단 관심, 좋지 않은 코드임..)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if (authUser == null) {
//			return "redirect:/user/login";
//		}
//		/////////////////////////////////////////////////////
		//@Auth로 접근 제어는 할 수 있지만.. authUser의 field가 필요한 경우... session이 필요함..!!
		UserVo userVo = userSerivce.getUser(authUser.getNo());
		model.addAttribute("userVo", userVo);

		return "/user/update";
	}

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	//session을 빼고싶은 경우 - authUser arguments Resolver
	//public String update(@AuthUser UserVo authUser, UserVo userVo)
	public String update(@AuthUser UserVo authUser, UserVo userVo) {
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
		//접근 제어는 뻇지만.. 기술 침투는... 어쩔 수 없음... session을 사용...
		userVo.setNo(authUser.getNo());
		userSerivce.update(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
	
	//Controller 별로 Exception Handler 가능!
//	@ExceptionHandler(Exception.class)
//	public String handlerExecption() {
//		return "error/exception";
//	}
}
