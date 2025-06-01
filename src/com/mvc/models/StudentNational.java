package com.mvc.models;

public class StudentNational extends Student {

	private double varScholarshipPercentage;

	public StudentNational(String pName, String pLastnames, String pId, String pCarnet, String pNationality,
			double pScholarshipPercentage) {
		super(pName, pLastnames,pId, pCarnet,  pNationality);
		this.varScholarshipPercentage = pScholarshipPercentage;
	}

	public double getVarScholarshipPercentage() {
		return varScholarshipPercentage;
	}

	public void setVarScholarshipPercentage(double pScholarshipPercentage) {
		this.varScholarshipPercentage = pScholarshipPercentage;
	}

}