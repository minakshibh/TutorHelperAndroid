package com.equiworx.model;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Parcel;
import android.os.Parcelable;

public class Tutor implements Parcelable{
	private String tutorId, name, email, contactNumber, altContactNumber, address, gender,notes,studentid,fee,nolessoin,duefee,feeCollect,outStandingBalance,parentId,lessonIds;
	
	String yearname,feeDue,fee_collected, outstanding_balance,fee_outstanding;

	public String getNolessoin() {
		return nolessoin;
	}
	public void setNolessoin(String nolessoin) {
		this.nolessoin = nolessoin;
	}
	public String getDuefee() {
		return duefee;
	}
	public void setDuefee(String duefee) {
		this.duefee = duefee;
	}
	public String getOutStandingBalance() {
		return outStandingBalance;
	}
	public void setOutStandingBalance(String outStandingBalance) {
		this.outStandingBalance = outStandingBalance;
	}
	public String getLessonIds() {
		return lessonIds;
	}
	public void setLessonIds(String lessonIds) {
		this.lessonIds = lessonIds;
	}
	public String getYearname() {
		return yearname;
	}
	public void setYearname(String yearname) {
		this.yearname = yearname;
	}
	public String getFee_collected() {
		return fee_collected;
	}
	public void setFee_collected(String fee_collected) {
		this.fee_collected = fee_collected;
	}
	public String getOutstanding_balance() {
		return outstanding_balance;
	}
	public void setOutstanding_balance(String outstanding_balance) {
		this.outstanding_balance = outstanding_balance;
	}
	public String getFee_outstanding() {
		return fee_outstanding;
	}
	public void setFee_outstanding(String fee_outstanding) {
		this.fee_outstanding = fee_outstanding;
	}
	public String getTotal_Feedue() {
		return Total_Feedue;
	}
	public void setTotal_Feedue(String total_Feedue) {
		Total_Feedue = total_Feedue;
	}
	public String getTotal_Fee_Collected() {
		return Total_Fee_Collected;
	}
	public void setTotal_Fee_Collected(String total_Fee_Collected) {
		Total_Fee_Collected = total_Fee_Collected;
	}
	public String getTotal_OutstandingBalance() {
		return Total_OutstandingBalance;
	}
	public void setTotal_OutstandingBalance(String total_OutstandingBalance) {
		Total_OutstandingBalance = total_OutstandingBalance;
	}
	public String getParentId() {
		return parentId;
	}
	public static Parcelable.Creator<Tutor> getCreator() {
		return CREATOR;
	}
	public void setFeeDue(String feeDue) {
		this.feeDue = feeDue;
	}

	ArrayList<StudentIdFee> student;
	public String getStudentid() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public ArrayList<StudentIdFee> getStudent() {
		return student;
	}
	public void setStudent(ArrayList<StudentIdFee> student) {
		this.student = student;
	}
	public Tutor(){
		
	}
	 public Tutor(Parcel source) {
		 tutorId = source.readString();
		 name = source.readString();
		 email = source.readString();
		 contactNumber = source.readString();
		 altContactNumber = source.readString();
		 address = source.readString();
		 gender = source.readString();
    }
	 
	 
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTutorId() {
		return tutorId;
	}

	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
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

	public static final Parcelable.Creator<Tutor> CREATOR
	  = new Parcelable.Creator<Tutor>() 
	{
	       public Tutor createFromParcel(Parcel in) 
	       {
	           return new Tutor(in);
	       }

	       public Tutor[] newArray (int size) 
	       {
	           return new Tutor[size];
	       }
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return this.hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {	 
		 
		dest.writeString(tutorId);
		dest.writeString(name);
		dest.writeString(email);
		dest.writeString(contactNumber);
		dest.writeString(altContactNumber);
		dest.writeString(address);
		dest.writeString(gender);
	}
	public void setNoLession(String nolessoin) {
		// TODO Auto-generated method stub
		this.nolessoin=nolessoin;
	}
	public void setDueFee(String duefee) {
		// TODO Auto-generated method stub
		this.duefee=duefee;
	}
	public void setFeeCollect(String feeCollect) {
		// TODO Auto-generated method stub
		this.feeCollect=feeCollect;
	}
	public void setOutStandingBalnce(String outStandingBalance) {
		// TODO Auto-generated method stub
		this.outStandingBalance=outStandingBalance;
	}
	public String getNoLessons() {
		// TODO Auto-generated method stub
		return nolessoin;
	}
	public String getDueFee() {
		// TODO Auto-generated method stub
		return duefee;
	}
	public String getFeeCollect() {
		// TODO Auto-generated method stub
		return feeCollect;
	}
	public String getOutstandingBalnce() {
		// TODO Auto-generated method stub
		return outStandingBalance;
	}
	public void setParentId(String parentId) {
		// TODO Auto-generated method stub
		this.parentId=parentId;
	}
	public void setYearName(String yearname) {
		// TODO Auto-generated method stub
		this.yearname=yearname;
	}
	public String getYearName() {
		// TODO Auto-generated method stub
		return yearname;
	}
	public void setLseeonIds(String lessonIds) {
		// TODO Auto-generated method stub
		this.lessonIds=lessonIds;
	}

	public String getLseeonIds() {
		// TODO Auto-generated method stub
		return lessonIds;
	}
	String Total_Feedue,Total_Fee_Collected,Total_OutstandingBalance;
	public void setTotalFeeDue(String Total_Feedue) {
		// TODO Auto-generated method stub
		this.Total_Feedue=Total_Feedue;
	}

	public String getFeeDue() {
		// TODO Auto-generated method stub
		return Total_Feedue;
	}

	
}
