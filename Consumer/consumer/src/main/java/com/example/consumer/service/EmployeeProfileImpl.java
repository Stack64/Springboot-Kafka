package com.example.consumer.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.consumer.EmployeeConsumer;
import com.example.consumer.dao.EmployeeRepository;
import com.example.consumer.model.EmployeeVO;
@Service
public class EmployeeProfileImpl implements EmployeeProfile{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeProfileImpl.class);

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public void saveOrUpdate(EmployeeVO employeeVo) {
		try {
			Timestamp currentTimeStamp=new Timestamp(System.currentTimeMillis());
			employeeVo.setModified_time(currentTimeStamp);
			
			employeeRepository.save(employeeVo);
		}catch(Exception e) {
			logger.error("Error in Saving or Upating",e);
			throw e;
		}
	}

	@Override
	public EmployeeVO getEmployeeProfile(String pernr) {
		return employeeRepository.findById(pernr).orElseGet(null);
	}

}
