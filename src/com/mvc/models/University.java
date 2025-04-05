package com.mvc.models;

public class University {

	private String name;
	private String adress;
	private String phoneNumber;

	public University() {

	}

	public University(String name, String adress, String phoneNumber) {
		super();
		this.name = name;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String toString() {
		return "Nombre de la universidad: "+name+"Direccion: "+adress+"Numero de Telefono"+phoneNumber;
	}
}
