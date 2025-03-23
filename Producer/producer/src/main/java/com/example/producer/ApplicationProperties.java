package com.example.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {
	
	@Value("${request_time}")
	private String request_time;
    
	@Value("${modifed_time}")
	private String modifed_time;

	@Value("${user.id}")
	private String userId;
	
	@Value("${employee.name}")
	private String employeeName;
	
	@Value("${manager.user.id}")
	private String managerUserId;

	@Value("${email.id}")
	private String emailId;
	
	@Value("${mobile}")
	private String mobile;
	
	@Value("${domain.id}")
	private String domainid;
	
	@Value("${transaction.id}")
	private String transaction_id;
	
	@Value("${active.field}")
	private String activeField;
	
	@Value("${kafka.employee.profile.topic}")  
    private String employeeProfileTopic;
	
	public String getEmployeeProfileTopic() {
		return employeeProfileTopic;
	}

	public String getActiveField() {
		return activeField;
	}

	public String getRequest_time() {
		return request_time;
	}

	public String getModifed_time() {
		return modifed_time;
	}

	public String getUserId() {
		return userId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getManagerUserId() {
		return managerUserId;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getMobile() {
		return mobile;
	}

	public String getTransaction_id() {
		return transaction_id;
	}
	public String getDomainid() {
		return domainid;
	}
	
}
