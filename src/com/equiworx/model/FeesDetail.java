package com.equiworx.model;

public class FeesDetail implements FeeDetail{
	private String monthName, noOfLessons, feeDue, feeCollected, outstandingBalance, lessonIds, year,parentIds,fee_outstanding ;

	public FeesDetail(String monthName, String noOfLessons,	String feeDue, String feeCollected, String outstandingBalance, String lessonIds, String fee_outstanding) {
		// TODO Auto-generated constructor stub
		this.monthName=monthName;
		this.noOfLessons=noOfLessons;
		this.feeDue=feeDue;
		this.feeCollected=feeCollected;
		this.outstandingBalance=outstandingBalance;
		this.lessonIds=lessonIds;
		this.fee_outstanding=fee_outstanding;
	}

	

	public FeesDetail(String monthName, String noOfLessons, String feeDue,
			String feeCollected, String outstandingBalance, String lessonIds, String parentId, String fee_outstanding) {
		// TODO Auto-generated constructor stub
		this.monthName=monthName;
		this.noOfLessons=noOfLessons;
		this.feeDue=feeDue;
		this.feeCollected=feeCollected;
		this.outstandingBalance=outstandingBalance;
		this.lessonIds=lessonIds;
		this.parentIds=parentId;
		this.fee_outstanding=fee_outstanding;
	}



	public FeesDetail() {
		// TODO Auto-generated constructor stub
	}



	public String getFee_outstanding() {
		return fee_outstanding;
	}



	public void setFee_outstanding(String fee_outstanding) {
		this.fee_outstanding = fee_outstanding;
	}



	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getNoOfLessons() {
		return noOfLessons;
	}

	public void setNoOfLessons(String noOfLessons) {
		this.noOfLessons = noOfLessons;
	}

	public String getFeeDue() {
		return feeDue;
	}

	public void setFeeDue(String feeDue) {
		this.feeDue = feeDue;
	}

	public String getFeeCollected() {
		return feeCollected;
	}

	public void setFeeCollected(String feeCollected) {
		this.feeCollected = feeCollected;
	}

	public String getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(String outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public String getLessonIds() {
		return lessonIds;
	}

	public void setLessonIds(String lessonIds) {
		this.lessonIds = lessonIds;
	}

	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return false;
	}
}
