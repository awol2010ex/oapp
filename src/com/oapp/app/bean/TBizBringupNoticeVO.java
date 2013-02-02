package com.oapp.app.bean;

import java.io.Serializable;
//培训VO
public class TBizBringupNoticeVO  implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1335649285584653052L;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String title;
	private java.util.Date startdatetime;
	private java.util.Date enddatetime;
	private java.lang.String notifyway;
	private java.lang.String address;
	private java.util.Date savedatetime;
	private java.util.Date issuedatetime;
	private java.lang.Integer status;
	private java.lang.String staffid;
	private java.lang.String courseid;
	private java.lang.String courseName;
	private java.lang.String otherThing;
	private java.util.Date lastUpdateTime;
	private java.lang.Integer batch;
	private java.lang.Integer isAnswer;
	private java.lang.Integer writeStatus;
	private java.util.Date firstReaderTime;
	private java.lang.String noticeDtlId;

	private java.lang.Integer isnotanize; /*�����Ƿ���ȷ��*/
	
	private java.lang.Integer iscancel;//�Ƿ��ѳ���(1��0��)
	
	//�����ֶ�
	private String staffname;
	private String email;
	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.lang.Integer getIsnotanize() {
		return isnotanize;
	}

	public void setIsnotanize(java.lang.Integer isnotanize) {
		this.isnotanize = isnotanize;
	}

	public java.lang.String getNoticeDtlId() {
		return noticeDtlId;
	}

	public void setNoticeDtlId(java.lang.String noticeDtlId) {
		this.noticeDtlId = noticeDtlId;
	}

	public java.lang.Integer getWriteStatus() {
		return writeStatus;
	}

	public void setWriteStatus(java.lang.Integer writeStatus) {
		this.writeStatus = writeStatus;
	}

	public java.util.Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public java.lang.String getOtherThing() {
		return otherThing;
	}

	public void setOtherThing(java.lang.String otherThing) {
		this.otherThing = otherThing;
	}

	public java.lang.String getCourseName() {
		return courseName;
	}

	public void setCourseName(java.lang.String courseName) {
		this.courseName = courseName;
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
	 * Return the value associated with the column: TITLE
	 */
	public java.lang.String getTitle() {
		return title;
	}

	/**
	 * Set the value related to the column: TITLE
	 * @param title the TITLE value
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	/**
	 * Return the value associated with the column: STARTDATETIME
	 */
	public java.util.Date getStartdatetime() {
		return startdatetime;
	}

	/**
	 * Set the value related to the column: STARTDATETIME
	 * @param startdatetime the STARTDATETIME value
	 */
	public void setStartdatetime(java.util.Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	/**
	 * Return the value associated with the column: ENDDATETIME
	 */
	public java.util.Date getEnddatetime() {
		return enddatetime;
	}

	/**
	 * Set the value related to the column: ENDDATETIME
	 * @param enddatetime the ENDDATETIME value
	 */
	public void setEnddatetime(java.util.Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	

	

	public java.lang.String getNotifyway() {
		return notifyway;
	}

	public void setNotifyway(java.lang.String notifyway) {
		this.notifyway = notifyway;
	}

	/**
	 * Return the value associated with the column: ADDRESS
	 */
	public java.lang.String getAddress() {
		return address;
	}

	/**
	 * Set the value related to the column: ADDRESS
	 * @param address the ADDRESS value
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	/**
	 * Return the value associated with the column: SAVEDATETIME
	 */
	public java.util.Date getSavedatetime() {
		return savedatetime;
	}

	/**
	 * Set the value related to the column: SAVEDATETIME
	 * @param savedatetime the SAVEDATETIME value
	 */
	public void setSavedatetime(java.util.Date savedatetime) {
		this.savedatetime = savedatetime;
	}

	/**
	 * Return the value associated with the column: ISSUEDATETIME
	 */
	public java.util.Date getIssuedatetime() {
		return issuedatetime;
	}

	/**
	 * Set the value related to the column: ISSUEDATETIME
	 * @param issuedatetime the ISSUEDATETIME value
	 */
	public void setIssuedatetime(java.util.Date issuedatetime) {
		this.issuedatetime = issuedatetime;
	}

	/**
	 * Return the value associated with the column: STATUS
	 */
	public java.lang.Integer getStatus() {
		return status;
	}

	/**
	 * Set the value related to the column: STATUS
	 * @param status the STATUS value
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	/**
	 * Return the value associated with the column: STAFFID
	 */
	public java.lang.String getStaffid() {
		return staffid;
	}

	/**
	 * Set the value related to the column: STAFFID
	 * @param staffid the STAFFID value
	 */
	public void setStaffid(java.lang.String staffid) {
		this.staffid = staffid;
	}

	/**
	 * Return the value associated with the column: COURSEID
	 */
	public java.lang.String getCourseid() {
		return courseid;
	}

	/**
	 * Set the value related to the column: COURSEID
	 * @param courseid the COURSEID value
	 */
	public void setCourseid(java.lang.String courseid) {
		this.courseid = courseid;
	}

	public String toString() {
		return super.toString();
	}

	public java.lang.Integer getBatch() {
		return batch;
	}

	public void setBatch(java.lang.Integer batch) {
		this.batch = batch;
	}

	public java.lang.Integer getIsAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(java.lang.Integer isAnswer) {
		this.isAnswer = isAnswer;
	}

	public java.util.Date getFirstReaderTime() {
		return firstReaderTime;
	}

	public void setFirstReaderTime(java.util.Date firstReaderTime) {
		this.firstReaderTime = firstReaderTime;
	}

	public java.lang.Integer getIscancel() {
		return iscancel;
	}

	public void setIscancel(java.lang.Integer iscancel) {
		this.iscancel = iscancel;
	}

}