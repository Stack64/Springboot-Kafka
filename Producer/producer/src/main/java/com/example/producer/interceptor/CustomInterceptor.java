package com.example.producer.interceptor;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);
	
	private static final ThreadLocal<String> threadId = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String generatedThreadId = UUID.randomUUID().toString(); // Generate unique ID
        threadId.set(generatedThreadId);
        logger.info("Generated Thread ID: " + generatedThreadId);
        return true; 
    }

    public static String getThreadId() {
        return threadId.get();
    }

    public static void clear() {
    	logger.info("Clearing Thread ID: {}", threadId.get());
        threadId.remove();
    }
	
//	public boolean preHandle(HttpServletRequest request , HttpServletResponse response, Object handler) throws Exception{
//		
//		
//	}
//	public void postHandle(HttpServletRequest request , HttpServletResponse response, Object handler) throws Exception{
//		
//		
//	}
}
