package com.dwf.switchstore.sf.model;

import java.time.LocalDate;
import java.util.Date;

public class Estudiantes {
    private int id;
    private String dui;
    private String nombreCompleto;
    private String direccion;
    private String email;
    private LocalDate fechaNacimiento; // Cambiado a LocalDate
    private String telefono;
    private String sexo;

    public Estudiantes() {
    }

    public Estudiantes(int id, String dui, String nombreCompleto, String direccion, String email, LocalDate fechaNacimiento, String telefono, String sexo) {
        this.id = id;
        this.dui = dui;
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.sexo = sexo;
    }

    // Getters y Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDui() { return dui; }

    public void setDui(String dui) { this.dui = dui; }

    public String getNombreCompleto() { return nombreCompleto; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getSexo() { return sexo; }

    public void setSexo(String sexo) { this.sexo = sexo; }
}
