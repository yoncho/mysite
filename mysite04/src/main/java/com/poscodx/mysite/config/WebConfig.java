package com.poscodx.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.poscodx.mysite.config.web.MessageSourceConfig;
import com.poscodx.mysite.config.web.MvcConfig;
import com.poscodx.mysite.event.ApplicationContextEventListener;

@Configuration
@EnableAspectJAutoProxy //<!-- auto asepct(proxy) -->
@ComponentScan({"com.poscodx.mysite.controller", "com.poscodx.mysite.exception"}) //<!-- controller & component -->
@Import({MvcConfig.class,MessageSourceConfig.class})
public class WebConfig {
	
	//Application ContextEventListener
	@Bean
	public ApplicationContextEventListener applicationContextEventListener() {
		return new ApplicationContextEventListener();
	}
	
}
