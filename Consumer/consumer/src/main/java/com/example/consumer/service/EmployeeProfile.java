package com.example.consumer.service;

import com.example.consumer.model.EmployeeVO;

public interface EmployeeProfile {
 
	void saveOrUpdate(EmployeeVO employeeVo);
	
	EmployeeVO getEmployeeProfile(String pernr);
}
