package com.example.gym.dto;

public class ClaseDetalle {
    private  int idClase;
    private  String entrenador;
    private  String nombreClase;
    private  String diaSemana;
    private  String horaInicio;
    private  String horaFin;
    private  Integer cupoMaximo;

    public ClaseDetalle(int idClase, String entrenador, String nombreClase, String diaSemana,
                        String horaInicio, String horaFin, Integer cupoMaximo) {
        this.idClase = idClase;
        this.entrenador = entrenador;
        this.nombreClase = nombreClase;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cupoMaximo = cupoMaximo;
    }

    public int getIdClase() { return idClase; }
    public String getEntrenador() { return entrenador; }
    public String getNombreClase() { return nombreClase; }
    public String getDiaSemana() { return diaSemana; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
    public Integer getCupoMaximo() { return cupoMaximo; }
}