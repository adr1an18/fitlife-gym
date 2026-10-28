package com.example.gym.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gym.modelo.Entrenador;

public interface EntrenadorRepositorio extends JpaRepository<Entrenador, Integer> {
}