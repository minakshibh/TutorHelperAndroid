package com.equiworx.model;

import java.util.ArrayList;

public class MyLesson {

	private String result,
	message,greatest_last_updated,
	 LessonId, TutorId, 
	ParentId, ParentName, ParentEmail,
	maximumValueOfLastUpdated, lessonTopic, 
	lessonDescription, startTime, EndTime,
	Duration, Days, lessonDate, 
	isRecurring, isActive, studentList,
	lesson_tutor_id,
	tutor_name ,
	lessonenddate,
	studentno,studentName,studentNO,student_email,student_adress,lesson_is_active;
    public String getLesson_is_active() {
		return lesson_is_active;
	}

	public void setLesson_is_active(String lesson_is_active) {
		this.lesson_is_active = lesson_is_active;
	}

	ArrayList<StudentList> array_studentlist;
	public ArrayList<StudentList> getArray_studentlist() {
	return array_studentlist;
}

public void setArray_studentlist(ArrayList<StudentList> array_studentlist) {
	this.array_studentlist = array_studentlist;
}

	public String getStudentno() {
		return studentno;
	}

	public void setStudentno(String studentno) {
		this.studentno = studentno;
	}

	public String getLessonenddate() {
		return lessonenddate;
	}

	public void setLessonenddate(String lessonenddate) {
		this.lessonenddate = lessonenddate;
	}

	public String getLesson_tutor_id() {
		return lesson_tutor_id;
	}

	public void setLesson_tutor_id(String lesson_tutor_id) {
		this.lesson_tutor_id = lesson_tutor_id;
	}

	public String getGreatest_last_updated() {
		return greatest_last_updated;
	}

	public void setGreatest_last_updated(String greatest_last_updated) {
		this.greatest_last_updated = greatest_last_updated;
	}

	public String getTutor_name() {
		return tutor_name;
	}

	public void setTutor_name(String tutor_name) {
		this.tutor_name = tutor_name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLessonId() {
		return LessonId;
	}

	public void setLessonId(String lessonId) {
		LessonId = lessonId;
	}

	public String getTutorId() {
		return TutorId;
	}

	public void setTutorId(String tutorId) {
		TutorId = tutorId;
	}

	public String getParentId() {
		return ParentId;
	}

	public void setParentId(String parentId) {
		ParentId = parentId;
	}

	public String getParentName() {
		return ParentName;
	}

	public void setParentName(String parentName) {
		ParentName = parentName;
	}

	public String getParentEmail() {
		return ParentEmail;
	}

	public void setParentEmail(String parentEmail) {
		ParentEmail = parentEmail;
	}

	public String getMaximumValueOfLastUpdated() {
		return maximumValueOfLastUpdated;
	}

	public void setMaximumValueOfLastUpdated(String maximumValueOfLastUpdated) {
		this.maximumValueOfLastUpdated = maximumValueOfLastUpdated;
	}

	public String getLessonTopic() {
		return lessonTopic;
	}

	public void setLessonTopic(String lessonTopic) {
		this.lessonTopic = lessonTopic;
	}

	public String getLessonDescription() {
		return lessonDescription;
	}

	public void setLessonDescription(String lessonDescription) {
		this.lessonDescription = lessonDescription;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	public String getDays() {
		return Days;
	}

	public void setDays(String days) {
		Days = days;
	}

	public String getLessonDate() {
		return lessonDate;
	}

	public void setLessonDate(String lessonDate) {
		this.lessonDate = lessonDate;
	}

	public String getIsRecurring() {
		return isRecurring;
	}

	public void setIsRecurring(String isRecurring) {
		this.isRecurring = isRecurring;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getStudentList() {
		return studentList;
	}

	public void setStudentList(String studentList) {
		this.studentList = studentList;
	}

	public void setSName(String studentName) {
		// TODO Auto-generated method stub
		this.studentName=studentName;
	}

	public void setSEmail(String student_email) {
		// TODO Auto-generated method stub
		this.student_email=student_email;
	}

	public void setSAdress(String student_adress) {
		// TODO Auto-generated method stub
		this.student_adress=student_adress;
	}

	public void setSContact_Info(String studentNO) {
		// TODO Auto-generated method stub
		this.studentNO=studentNO;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return studentName;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return student_email;
	}

	public String getAddress() {
		// TODO Auto-generated method stub
		return student_adress;
	}

	public String getStudentfee() {
		// TODO Auto-generated method stub
		return null;
	}

	ArrayList<StudentList> studebtlist;
	public ArrayList<StudentList> getStudebtlist() {
		return studebtlist;
	}

	public void setStudebtlist(ArrayList<StudentList> studebtlist) {
		this.studebtlist = studebtlist;
	}
	
}
