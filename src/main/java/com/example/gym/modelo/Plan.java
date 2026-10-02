package com.example.gym.modelo;

public class Plan {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMeses;

    public Plan() {
    }

    public Plan(int id, String nombre, String descripcion, double precio, int duracionMeses) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMeses = duracionMeses;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getDuracionMeses() { return duracionMeses; }
    public void setDuracionMeses(int duracionMeses) { this.duracionMeses = duracionMeses; }
}