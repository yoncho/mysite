package com.poscodx.mysite.listener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;


public class SpringContainerEventListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private SiteService siteService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			
		}else {
			System.out.println("SpringContext Refreshed [root]");
		}
		
		System.out.println("SpringContext Refreshed [sub]");
		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) event.getApplicationContext();
		
		SiteVo site = siteService.getSite();
		applicationContext.getBeanFactory().registerSingleton("site", site);
	}
}
