package com.example.consumer;

import org.springframework.stereotype.Component;

import com.example.consumer.util.SystemConfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
@Component
public class ApplicationContextAwareImpl implements ApplicationContextAware {

	@Autowired
	SystemConfig systemConfig;
	
	private void startEmployeeConsumer(ApplicationContext applicationContext) {
		try {
			EmployeeConsumer employeeConsumer=null;
			for(int i=0;i<Integer.parseInt(systemConfig.topic1Partitions);i++) {
				employeeConsumer =applicationContext.getBean(EmployeeConsumer.class);
				employeeConsumer.setName("EmployeeConsumerThread" + i);
				employeeConsumer.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		startEmployeeConsumer(applicationContext);
	}
	
}
