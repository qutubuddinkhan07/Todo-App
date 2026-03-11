package com.todo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.todo.util.DatabaseInitializer;
import com.todo.util.JPAUtil;

@WebListener
public class AppContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Close JPA resources when application stops
		JPAUtil.close();
		System.out.println("Application stopped - JPA resources closed");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Initialize database when application starts
		DatabaseInitializer.initializeDatabase();
		System.out.println("Application started - JPA initialized");
	}
}
