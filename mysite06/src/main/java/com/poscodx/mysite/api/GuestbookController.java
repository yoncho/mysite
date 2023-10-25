package com.poscodx.mysite.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poscodx.mysite.dto.JsonResult;
import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;

@RestController("GuestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	//guestbook list
	@GetMapping
	public JsonResult getGuestbookList() {
		List<GuestbookVo> list = guestbookService.getContentsList();
		return JsonResult.success(list);
	}
	
	@PostMapping
	public JsonResult insertGuestBook(
			@RequestBody GuestbookVo vo) {
		System.out.println(vo.getName() + vo.getContents());
		guestbookService.addContents(vo);
		return JsonResult.success(vo);
	}
}
