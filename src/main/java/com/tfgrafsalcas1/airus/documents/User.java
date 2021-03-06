package com.tfgrafsalcas1.airus.documents;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    public String id;

    public String nombre;
    public String apellidos;
    public String correo;
    public LocalDate fechaNacimiento;

    public User() {}

    public User(String nombre, String apellidos, LocalDate fechaNacimiento, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
    }

    @Override
    public String toString() {
        return String.format(
            "User[id=%s, nombre='%s', apellidos='%s', fechaNacimiento='%s', correo='%s']",
            id, nombre, apellidos, fechaNacimiento, correo);
    }

	public String getId() {
		return null;
	}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
