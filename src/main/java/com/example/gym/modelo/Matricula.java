package com.example.gym.modelo;

public class Matricula {
    private int id;
    private Integer idCliente;
    private Integer idPlan;
    private String fechaInicio;
    private String fechaFin;

    public Matricula() {
    }

    public Matricula(int id, Integer idCliente, Integer idPlan, String fechaInicio, String fechaFin) {
        this.id = id;
        this.idCliente = idCliente;
        this.idPlan = idPlan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public Integer getIdPlan() { return idPlan; }
    public void setIdPlan(Integer idPlan) { this.idPlan = idPlan; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }
}