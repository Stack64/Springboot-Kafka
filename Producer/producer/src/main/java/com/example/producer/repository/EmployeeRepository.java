package com.example.producer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.producer.model.EmployeeMasterVO;

public interface EmployeeRepository extends JpaRepository<EmployeeMasterVO,String>{

	Optional<EmployeeMasterVO> findByPernr(String pernr);
}
