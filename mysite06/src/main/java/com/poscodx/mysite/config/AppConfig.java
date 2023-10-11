package com.poscodx.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.poscodx.mysite.config.app.DBConfig;
import com.poscodx.mysite.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy //<aop:aspectj-autoproxy/>
@ComponentScan(basePackages= {"com.poscodx.mysite.repository","com.poscodx.mysite.service", "com.poscodx.mysite.aspect","com.poscodx.mysite.listener"})
//<context:annotation-config />
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
