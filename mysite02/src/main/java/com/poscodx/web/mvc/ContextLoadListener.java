package com.poscodx.web.mvc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
    	System.out.println("application start.....s...");
    }
    
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }

}
