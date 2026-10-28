package com.example.gym.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gym.modelo.Plan;

public interface PlanRepositorio extends JpaRepository<Plan, Integer> {
    List<Plan> findByNombreContainingIgnoreCase(String texto);
}