package com.example.gym.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gym.dto.MatriculaDetalle;
import com.example.gym.modelo.Matricula;

public interface MatriculaRepositorio extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByIdCliente(Integer idCliente);

    void deleteByIdCliente(Integer idCliente);

    @Query("""
            SELECT new com.example.gym.dto.MatriculaDetalle(
                m.id, CONCAT(c.nombres, ' ', c.apellidos), p.nombre, p.precio,
                m.fechaInicio, m.fechaFin)
            FROM Matricula m, Cliente c, Plan p
            WHERE m.idCliente = c.id AND m.idPlan = p.id
            ORDER BY m.id
            """)
    List<MatriculaDetalle> listarDetalle();

    @Query("""
            SELECT new com.example.gym.dto.MatriculaDetalle(
                m.id, CONCAT(c.nombres, ' ', c.apellidos), p.nombre, p.precio,
                m.fechaInicio, m.fechaFin)
            FROM Matricula m, Cliente c, Plan p
            WHERE m.idCliente = c.id AND m.idPlan = p.id
              AND p.id = :idPlan AND c.id = :idCliente
            ORDER BY m.id
            """)
    List<MatriculaDetalle> listarDetallePorPlanYCliente(@Param("idPlan") Integer idPlan,
                                                       @Param("idCliente") Integer idCliente);
}