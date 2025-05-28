package com.mvc.models;

public class Student {

	protected String varId;
	protected String varCarnet;
	protected String varName;
	protected String varLastnames;
	protected String varNationality;
	

	public Student(String pId, String pCarnet, String pName, String pLastnames, String pNationality) {
		super();
		this.varId = pId;
		this.varCarnet = pCarnet;
		this.varName = pName;
		this.varLastnames = pLastnames;
		this.varNationality = pNationality;
		
	}
	
	
	
	public String getVarId() {
		return varId;
	}
	public void setVarId(String pId) {
		this.varId = pId;
	}
	public String getVarCarnet() {
		return varCarnet;
	}
	public void setVarCarnet(String pCarnet) {
		this.varCarnet = pCarnet;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String pName) {
		this.varName = pName;
	}
	public String getVarLastnames() {
		return varLastnames;
	}
	public void setVarLastnames(String pLastnames) {
		this.varLastnames = pLastnames;
	}
	public String getVarNationality() {
		return varNationality;
	}
	public void setVarNationality(String pNationality) {
		this.varNationality = pNationality;
	}
	
	
	
}
