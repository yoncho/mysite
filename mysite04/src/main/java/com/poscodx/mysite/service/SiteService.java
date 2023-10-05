package com.poscodx.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.SiteRepository;
import com.poscodx.mysite.vo.SiteVo;

@Service
public class SiteService {
	@Autowired
	private SiteRepository siteRepository;
	
	public SiteVo getSite() {
		long siteNo = 1; //TODO : 추후 Site Template을 설정해놓을 수 있게..
		return siteRepository.findByNo(siteNo);
	}
	
	public void updateSite(SiteVo vo){
		siteRepository.updateSite(vo);
	}
	
	
}
