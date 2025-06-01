package com.mvc.models;

public class Courses {

	private String varSiglas;
	private String varDescripcion;
	private String varNombreEscuela;
	
	
	public Courses(String pSiglas,String pDescripcion,String pNombreEscuela) {
		this.varSiglas=pSiglas;
		this.varDescripcion=pDescripcion;
		this.varNombreEscuela=pNombreEscuela;
	}


	public String getVarSiglas() {
		return varSiglas;
	}


	public void setVarSiglas(String pSiglas) {
		this.varSiglas = pSiglas;
	}


	public String getVarDescripcion() {
		return varDescripcion;
	}


	public void setVarDescripcion(String pDescripcion) {
		this.varDescripcion = pDescripcion;
	}


	public String getVarNombreEscuela() {
		return varNombreEscuela;
	}


	public void setVarNombreEscuela(String pNombreEscuela) {
		this.varNombreEscuela = pNombreEscuela;
	}
	
}
