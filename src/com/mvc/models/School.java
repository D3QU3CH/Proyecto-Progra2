package com.mvc.models;

public class School {

	private String varName;

	public School() {

	}

	public School(String pName) {
		this.varName = pName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String toString() {
		return "Escuela: " + varName;
	}
	
	

}
