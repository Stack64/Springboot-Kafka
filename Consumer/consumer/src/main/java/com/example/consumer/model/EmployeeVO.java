package com.example.consumer.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee_master")
public class EmployeeVO {

	@Id
	@Column(name="pernr")
	private String pernr;
	
	@Column(name="ename")
	private String ename;
	
	@Column(name="pernrmgr")
	private String pernrmgr;
	
	@Column(name="domainid")
	private String domainid;
	
	@Column(name="emailid")
	private String emailid;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="transaction_id")
	private String transactionid;
	
	@Column(name="requestTime")
	private Timestamp request_time;
	
	@Column(name="modified_time")
	private Timestamp modified_time;
	
	@Column(name="isactive")
	private boolean active;

	public String getPernr() {
		return pernr;
	}

	public void setPernr(String pernr) {
		this.pernr = pernr;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getPernrmgr() {
		return pernrmgr;
	}

	public void setPernrmgr(String pernrmgr) {
		this.pernrmgr = pernrmgr;
	}

	public String getDomainid() {
		return domainid;
	}

	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public Timestamp getRequest_time() {
		return request_time;
	}

	public void setRequest_time(Timestamp request_time) {
		this.request_time = request_time;
	}

	public Timestamp getModified_time() {
		return modified_time;
	}

	public void setModified_time(Timestamp modified_time) {
		this.modified_time = modified_time;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
