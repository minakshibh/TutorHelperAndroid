package com.equiworx.model;

import java.util.ArrayList;

public class InvoiceModel {

	public String yearName;
	public String InvoiceUrl;
	public String monthName;
	public ArrayList<InvoiceDetail> arrayList;
	
	public ArrayList<InvoiceDetail> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<InvoiceDetail> arrayList) {
		this.arrayList = arrayList;
	}

	public String getYearName() {
		return yearName;
	}
	
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	
	public String getInvoiceUrl() {
		return InvoiceUrl;
	}
	
	public void setInvoiceUrl(String invoiceUrl) {
		InvoiceUrl = invoiceUrl;
	}
	
	public String getMonthName() {
		return monthName;
	}
	
	public void setMonthName(String monthName) {
		// TODO Auto-generated method stub
		this.monthName = monthName;
	}

	
	
}
