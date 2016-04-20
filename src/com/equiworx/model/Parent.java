package com.equiworx.model;

public class Parent {

	private String parentId, name, email, contactNumber, altContactNumber, address, credits, gender, pin, studentCount, lessonCount, 
	outstandingBalance, notes,activestudents,block_out_time_for_fullday,block_out_time_for_halfday,no_of_lessons,lesson_date,is_first;

	public String getIs_first() {
		return is_first;
	}
	public void setIs_first(String is_first) {
		this.is_first = is_first;
	}
	public String getLesson_date() {
		return lesson_date;
	}
	public void setLesson_date(String lesson_date) {
		this.lesson_date = lesson_date;
	}
	public String getBlock_out_time_for_fullday() {
		return block_out_time_for_fullday;
	}
	public void setBlock_out_time_for_fullday(String block_out_time_for_fullday) {
		this.block_out_time_for_fullday = block_out_time_for_fullday;
	}
	public String getBlock_out_time_for_halfday() {
		return block_out_time_for_halfday;
	}
	public void setBlock_out_time_for_halfday(String block_out_time_for_halfday) {
		this.block_out_time_for_halfday = block_out_time_for_halfday;
	}
	public String getNo_of_lessons() {
		return no_of_lessons;
	}
	public void setNo_of_lessons(String no_of_lessons) {
		this.no_of_lessons = no_of_lessons;
	}
	public String getActivestudents() {
		return activestudents;
	}
	public void setActivestudents(String activestudents) {
		this.activestudents = activestudents;
	}
	public Parent(String parentId, String name,String address,String gender)
	{
		 super();
		    this.parentId = parentId;
		    this.name = name;
		    this.address = address;
		    this.gender = gender;
	}
	public Parent()
	{
		
	}
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAltContactNumber() {
		return altContactNumber;
	}

	public void setAltContactNumber(String altContactNumber) {
		this.altContactNumber = altContactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
	public String getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(String studentCount) {
		this.studentCount = studentCount;
	}
public String getLessonCount() {
		return lessonCount;
	}

	public void setLessonCount(String lessonCount) {
		this.lessonCount = lessonCount;
	}

	public String getOutstandingBalance() {
		return outstandingBalance;
	}
	public String string() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(String outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

}
