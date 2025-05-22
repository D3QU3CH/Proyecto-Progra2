package com.mvc.models;

public class Teacher {
	String varName;
	String varFirstSurname;
	String varSecondSurname;
	String varId;
	String varGroup;
	String varSiglasCourses;
	
	public Teacher(String varName, String varFirstSurname, String varSecondSurname, String varId, String varGroup,
			String varSiglasCourses) {
		super();
		this.varName = varName;
		this.varFirstSurname = varFirstSurname;
		this.varSecondSurname = varSecondSurname;
		this.varId = varId;
		this.varGroup = varGroup;
		this.varSiglasCourses = varSiglasCourses;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarSecondSurname() {
		return varSecondSurname;
	}

	public void setVarSecondSurname(String varSecondSurname) {
		this.varSecondSurname = varSecondSurname;
	}

	public String getVarId() {
		return varId;
	}

	public void setVarId(String varId) {
		this.varId = varId;
	}

	public String getVarGroup() {
		return varGroup;
	}

	public void setVarGroup(String varGroup) {
		this.varGroup = varGroup;
	}

	public String getVarSiglasCourses() {
		return varSiglasCourses;
	}

	public void setVarSiglasCourses(String varSiglasCourses) {
		this.varSiglasCourses = varSiglasCourses;
	}

	@Override
	public String toString() {
		return "Teacher [varName=" + varName + ", varFirstSurname=" + varFirstSurname + ", varSecondSurname="
				+ varSecondSurname + ", varId=" + varId + ", varGroup=" + varGroup + ", varSiglasCourses="
				+ varSiglasCourses + "]";
	}
	
	
	
	

}
