package com.equiworx.model;

public class Statement {

	String parentlist,
	 ID,
     tutor_id,
     tutor_name,
     parent_id,
     parent_name,
     payment_mode,
     remarks,
     fee_paid,
     last_updated,session_dates,lesson_id;

	public String getSession_dates() {
		return session_dates;
	}

	public void setSession_dates(String session_dates) {
		this.session_dates = session_dates;
	}

	public String getLesson_id() {
		return lesson_id;
	}

	public void setLesson_id(String lesson_id) {
		this.lesson_id = lesson_id;
	}

	public String getParentlist() {
		return parentlist;
	}

	public void setParentlist(String parentlist) {
		this.parentlist = parentlist;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTutor_id() {
		return tutor_id;
	}

	public void setTutor_id(String tutor_id) {
		this.tutor_id = tutor_id;
	}

	public String getTutor_name() {
		return tutor_name;
	}

	public void setTutor_name(String tutor_name) {
		this.tutor_name = tutor_name;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFee_paid() {
		return fee_paid;
	}

	public void setFee_paid(String fee_paid) {
		this.fee_paid = fee_paid;
	}

	public String getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}
}
