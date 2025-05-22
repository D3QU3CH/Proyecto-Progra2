package com.mvc.models;

public class School {

	private String varName;
	private String varDirector;
	
	public School() {

	}

	public School(String pName) {
		this.varName = pName;
		this.varDirector = null;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	public String getVarDirector() {
		return varDirector;
	}

	public void setVarDirector(String varDirector) {
		this.varDirector = varDirector;
	}

	
	public String toString() {
		return "Escuela: " + varName + " - Director"+varDirector;
	}
	
	

}
