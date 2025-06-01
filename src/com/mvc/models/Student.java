package com.mvc.models;

public class Student {
	
	protected String varName;
	protected String varLastnames;
	protected String varId;
	protected String varCarnet;
	protected String varNationality;
	

	public Student(String pName,String pLastnames,String pId, String pCarnet,String pNationality) {
		super();
		this.varName = pName;
		this.varLastnames = pLastnames;
		this.varId = pId;
		this.varCarnet = pCarnet;
		this.varNationality = pNationality;
		
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
	
	public String getVarNationality() {
		return varNationality;
	}
	public void setVarNationality(String pNationality) {
		this.varNationality = pNationality;
	}
	
	
	
}