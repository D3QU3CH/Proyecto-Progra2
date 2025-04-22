package com.mvc.models;
import java.util.ArrayList;
import java.util.List;

public class University {

	private String name;
	private String adress;
	private String phoneNumber;
	private List<Escuela> escuelas;
	
	public University() {
		escuelas = new ArrayList<>();
	}

	public University(String name, String adress, String phoneNumber) {
		super();
		this.name = name;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
		this.escuelas = new ArrayList<>();
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

	 public void actualizarDatos(String nuevaDireccion, String nuevoTelefono) {
	        this.adress = nuevaDireccion;
	        this.phoneNumber = nuevoTelefono;
    }

    public void agregarEscuela(Escuela nuevaEscuela) {
        if (nuevaEscuela != null) {
            escuelas.add(nuevaEscuela);
        }
    }

    public List<Escuela> getEscuelas() {
        return escuelas;
    }

    public String toString() {
        return "Nombre de la universidad: " + name +
               ", Dirección: " + adress +
               ", Número de Teléfono: " + phoneNumber;
    }
}
