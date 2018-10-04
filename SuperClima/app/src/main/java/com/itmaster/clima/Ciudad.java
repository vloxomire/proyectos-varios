package com.itmaster.clima;

public class Ciudad {
	private String nombre;

	@Override
	public String toString() {
		return this.nombre;
	}
	
	public Ciudad(String n) {
		this.nombre = n;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
