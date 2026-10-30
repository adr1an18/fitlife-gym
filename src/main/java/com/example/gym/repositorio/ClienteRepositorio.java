package com.example.gym.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gym.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrDniContaining(
            String nombres, String apellidos, String dni);

    @Query("""
            SELECT c FROM Cliente c
            WHERE c.id IN (SELECT m.idCliente FROM Matricula m WHERE m.idPlan = :idPlan)
            ORDER BY c.nombres
            """)
    List<Cliente> listarPorPlan(@Param("idPlan") Integer idPlan);
}