package com.example.gym.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gym.modelo.Matricula;

public interface MatriculaRepositorio extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByIdCliente(Integer idCliente);

    void deleteByIdCliente(Integer idCliente);
}