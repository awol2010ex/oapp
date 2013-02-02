package com.oapp.app.bean;

import java.io.Serializable;


//信息VO
public class TBizInfomationReleaseLookVO 
		implements
			Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5306327334281768002L;

	// primary key
	private java.lang.String id;
	private java.lang.String sortName;
	private java.lang.String infoTitle;
	private java.lang.String staffName;
	private java.lang.String unitName;
	private java.util.Date  issueDateTime;
	private java.lang.Integer readerState;
	private java.lang.Integer instancy;
	private String email;;
	
	private Integer iscancel;
	public Integer getIscancel() {
		return iscancel;
	}
	public void setIscancel(Integer iscancel) {
		this.iscancel = iscancel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.lang.Integer getInstancy() {
		return instancy;
	}
	public void setInstancy(java.lang.Integer instancy) {
		this.instancy = instancy;
	}
	public java.lang.Integer getReaderState() {
		return readerState;
	}
	public void setReaderState(java.lang.Integer readerState) {
		this.readerState = readerState;
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getSortName() {
		return sortName;
	}
	public void setSortName(java.lang.String sortName) {
		this.sortName = sortName;
	}
	public java.lang.String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(java.lang.String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public java.lang.String getStaffName() {
		return staffName;
	}
	public void setStaffName(java.lang.String staffName) {
		this.staffName = staffName;
	}
	public java.lang.String getUnitName() {
		return unitName;
	}
	public void setUnitName(java.lang.String unitName) {
		this.unitName = unitName;
	}
	public java.util.Date getIssueDateTime() {
		return issueDateTime;
	}
	public void setIssueDateTime(java.util.Date issueDateTime) {
		this.issueDateTime = issueDateTime;
	}
	

}