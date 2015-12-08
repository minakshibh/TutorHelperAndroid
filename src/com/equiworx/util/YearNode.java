package com.equiworx.util;

import com.equiworx.model.FeeDetail;

public class YearNode implements FeeDetail {
	String lessonIds, year;

	public YearNode(String year) {
		// TODO Auto-generated constructor stub

		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return true;
	}
}
