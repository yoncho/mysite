package com.poscodx.mysite.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
	
	//	<!-- Connection Pool DataSource -->
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mariadb://192.168.0.181:3307/webdb?chraset=utf8");
		dataSource.setUsername("mysite");
		dataSource.setPassword("mysite");
		dataSource.setInitialSize(10);
		dataSource.setMaxActive(20); //최대 동시 활성 query 개수
		return dataSource;
	}
	
	
}
