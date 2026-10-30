package com.example.gym.dto;

public class MatriculaDetalle {
    private int idMatricula;
    private String cliente;
    private String plan;
    private double precio;
    private String fechaInicio;
    private String fechaFin;

    public MatriculaDetalle(int idMatricula, String cliente, String plan, double precio,
                            String fechaInicio, String fechaFin) {
        this.idMatricula = idMatricula;
        this.cliente = cliente;
        this.plan = plan;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdMatricula() { return idMatricula; }
    public String getCliente() { return cliente; }
    public String getPlan() { return plan; }
    public double getPrecio() { return precio; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaFin() { return fechaFin; }
}