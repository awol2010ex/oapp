package com.oapp.app.bean;

import java.io.Serializable;

//信息发布VO
public class TBizInfomationReleaseVO
		implements
			Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 9101276011410809152L;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String infoTitle;
	private java.lang.String infoContent;
	private java.lang.Integer instancyGrade;
	private java.lang.Integer sendAwake;
	private java.util.Date saveDatetime;
	private java.util.Date issueDatetime;
	private java.lang.Integer isissue;
	private java.lang.String infotypeId;
	private java.lang.String staffid;
	private java.lang.String staffName;
	private java.lang.String strDate;
	private java.lang.String templetId;
	
	private Integer iscancel;

	public Integer getIscancel() {
		return iscancel;
	}

	public void setIscancel(Integer iscancel) {
		this.iscancel = iscancel;
	}

	public java.lang.String getTempletId() {
		return templetId;
	}

	public void setTempletId(java.lang.String templetId) {
		this.templetId = templetId;
	}

	public java.lang.String getStrDate() {
		return strDate;
	}

	public void setStrDate(java.lang.String strDate) {
		this.strDate = strDate;
	}

	public java.lang.String getStaffName() {
		return staffName;
	}

	public void setStaffName(java.lang.String staffName) {
		this.staffName = staffName;
	}

	/**
	 * Return the unique identifier of this class
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * Return the value associated with the column: INFO_TITLE
	 */
	public java.lang.String getInfoTitle() {
		return infoTitle;
	}

	/**
	 * Set the value related to the column: INFO_TITLE
	 * @param infoTitle the INFO_TITLE value
	 */
	public void setInfoTitle(java.lang.String infoTitle) {
		this.infoTitle = infoTitle;
	}

	/**
	 * Return the value associated with the column: INFO_CONTENT
	 */
	public java.lang.String getInfoContent() {
		return infoContent;
	}

	/**
	 * Set the value related to the column: INFO_CONTENT
	 * @param infoContent the INFO_CONTENT value
	 */
	public void setInfoContent(java.lang.String infoContent) {
		this.infoContent = infoContent;
	}

	/**
	 * Return the value associated with the column: INSTANCY_GRADE
	 */
	public java.lang.Integer getInstancyGrade() {
		return instancyGrade;
	}

	/**
	 * Set the value related to the column: INSTANCY_GRADE
	 * @param instancyGrade the INSTANCY_GRADE value
	 */
	public void setInstancyGrade(java.lang.Integer instancyGrade) {
		this.instancyGrade = instancyGrade;
	}

	

	public java.lang.Integer getSendAwake() {
		return sendAwake;
	}

	public void setSendAwake(java.lang.Integer sendAwake) {
		this.sendAwake = sendAwake;
	}

	/**
	 * Return the value associated with the column: SAVE_DATETIME
	 */
	public java.util.Date getSaveDatetime() {
		return saveDatetime;
	}

	/**
	 * Set the value related to the column: SAVE_DATETIME
	 * @param saveDatetime the SAVE_DATETIME value
	 */
	public void setSaveDatetime(java.util.Date saveDatetime) {
		this.saveDatetime = saveDatetime;
	}

	/**
	 * Return the value associated with the column: ISSUE_DATETIME
	 */
	public java.util.Date getIssueDatetime() {
		return issueDatetime;
	}

	/**
	 * Set the value related to the column: ISSUE_DATETIME
	 * @param issueDatetime the ISSUE_DATETIME value
	 */
	public void setIssueDatetime(java.util.Date issueDatetime) {
		this.issueDatetime = issueDatetime;
	}

	/**
	 * Return the value associated with the column: ISISSUE
	 */
	public java.lang.Integer getIsissue() {
		return isissue;
	}

	/**
	 * Set the value related to the column: ISISSUE
	 * @param isissue the ISISSUE value
	 */
	public void setIsissue(java.lang.Integer isissue) {
		this.isissue = isissue;
	}

	/**
	 * Return the value associated with the column: INFOTYPE_ID
	 */
	public java.lang.String getInfotypeId() {
		return infotypeId;
	}

	/**
	 * Set the value related to the column: INFOTYPE_ID
	 * @param infotypeId the INFOTYPE_ID value
	 */
	public void setInfotypeId(java.lang.String infotypeId) {
		this.infotypeId = infotypeId;
	}

	public String toString() {
		return super.toString();
	}

	public java.lang.String getStaffid() {
		return staffid;
	}

	public void setStaffid(java.lang.String staffid) {
		this.staffid = staffid;
	}

}