package com.example.gym.modelo;

public class Cliente {
    private int id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String fechaNacimiento;

    public Cliente() {
    }

    public Cliente(int id, String nombres, String apellidos, String dni, String telefono, String fechaNacimiento) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}