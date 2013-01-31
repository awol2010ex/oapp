package com.oapp.app.bean;

import java.io.Serializable;

public class TBizInfomationReleaseQueryVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2227530621596442712L;
	// primary key
	private java.lang.String id;
	// fields
	private java.lang.String infoTitle;// 标题
	private java.lang.Integer instancyGrade;// 紧急程度
	private java.util.Date saveDatetime;// 保存时间
	private java.util.Date issueDatetime;// 发布时间
	private java.lang.Integer isissue;// 发布状态
	private java.lang.String infotypeId;// 类型id
	private java.lang.String infotypeName;// 类型名(不保存)
	private java.lang.String staffid;// 发布人id
	private java.lang.String staffname;// 发布人姓名
	private Integer iscancel;// 是否已撤销

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(java.lang.String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public java.lang.Integer getInstancyGrade() {
		return instancyGrade;
	}

	public void setInstancyGrade(java.lang.Integer instancyGrade) {
		this.instancyGrade = instancyGrade;
	}

	public java.util.Date getSaveDatetime() {
		return saveDatetime;
	}

	public void setSaveDatetime(java.util.Date saveDatetime) {
		this.saveDatetime = saveDatetime;
	}

	public java.util.Date getIssueDatetime() {
		return issueDatetime;
	}

	public void setIssueDatetime(java.util.Date issueDatetime) {
		this.issueDatetime = issueDatetime;
	}

	public java.lang.Integer getIsissue() {
		return isissue;
	}

	public void setIsissue(java.lang.Integer isissue) {
		this.isissue = isissue;
	}

	public java.lang.String getInfotypeId() {
		return infotypeId;
	}

	public void setInfotypeId(java.lang.String infotypeId) {
		this.infotypeId = infotypeId;
	}

	public java.lang.String getInfotypeName() {
		return infotypeName;
	}

	public void setInfotypeName(java.lang.String infotypeName) {
		this.infotypeName = infotypeName;
	}

	public java.lang.String getStaffid() {
		return staffid;
	}

	public void setStaffid(java.lang.String staffid) {
		this.staffid = staffid;
	}

	public java.lang.String getStaffname() {
		return staffname;
	}

	public void setStaffname(java.lang.String staffname) {
		this.staffname = staffname;
	}

	public Integer getIscancel() {
		return iscancel;
	}

	public void setIscancel(Integer iscancel) {
		this.iscancel = iscancel;
	}
}
