package com.equiworx.model;

public class Lesson_Booked {
	String ID;
	String description;
    String start_timing;
    String end_timing,date;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStart_timing() {
		return start_timing;
	}
	public void setStart_timing(String start_timing) {
		this.start_timing = start_timing;
	}
	public String getEnd_timing() {
		return end_timing;
	}
	public void setEnd_timing(String end_timing) {
		this.end_timing = end_timing;
	}




}
