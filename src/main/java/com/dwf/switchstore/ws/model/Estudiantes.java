package com.dwf.switchstore.ws.model;

/**
 * The Estudiante class represents a student entity with properties such as id, dui,
 * nombreCompleto, direccion, email, fechaNacimiento, telefono, and sexo.
 * It provides getter and setter methods for these properties.
 */
public class Estudiantes {

    private int id; // Unique identifier for the student
    private String dui; // DUI of the student
    private String nombreCompleto; // Full name of the student
    private String direccion; // Address of the student
    private String email; // Email of the student
    private String fechaNacimiento; // Birthdate of the student
    private String telefono; // Phone number of the student
    private String sexo; // Gender of the student

    /**
     * Gets the unique identifier for the student.
     *
     * @return the id of the student
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the student.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the DUI of the student.
     *
     * @return the dui of the student
     */
    public String getDui() {
        return dui;
    }

    /**
     * Sets the DUI of the student.
     *
     * @param dui the dui to set
     */
    public void setDui(String dui) {
        this.dui = dui;
    }

    /**
     * Gets the full name of the student.
     *
     * @return the nombreCompleto of the student
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Sets the full name of the student.
     *
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Gets the address of the student.
     *
     * @return the direccion of the student
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the address of the student.
     *
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Gets the email of the student.
     *
     * @return the email of the student
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the student.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the birthdate of the student.
     *
     * @return the fechaNacimiento of the student
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the birthdate of the student.
     *
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Gets the phone number of the student.
     *
     * @return the telefono of the student
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the phone number of the student.
     *
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Gets the gender of the student.
     *
     * @return the sexo of the student
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Sets the gender of the student.
     *
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
