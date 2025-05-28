package com.mvc.models;

public class StudentNational extends Student {

	private double varScholarshipPercentage;

	public StudentNational(String pId, String pCarnet, String pName, String pLastnames, String pNationality,
			double pScholarshipPercentage) {
		super(pId, pCarnet, pName, pLastnames, pNationality);
		this.varScholarshipPercentage = pScholarshipPercentage;
	}

	public double getVarScholarshipPercentage() {
		return varScholarshipPercentage;
	}

	public void setVarScholarshipPercentage(double pScholarshipPercentage) {
		this.varScholarshipPercentage = pScholarshipPercentage;
	}

}
