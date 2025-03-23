package com.example.consumer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.consumer.model.EmployeeVO;

public interface EmployeeRepository extends JpaRepository<EmployeeVO,String>{

}
