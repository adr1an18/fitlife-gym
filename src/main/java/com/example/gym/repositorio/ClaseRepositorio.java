package com.example.gym.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gym.dto.ClaseDetalle;
import com.example.gym.modelo.Clase;

public interface ClaseRepositorio extends JpaRepository<Clase, Integer> {

    List<Clase> findByIdEntrenador(Integer idEntrenador);

    @Query("""
            SELECT new com.example.gym.dto.ClaseDetalle(
                c.id, e.nombres, c.nombreClase, c.diaSemana,
                c.horaInicio, c.horaFin, c.cupoMaximo)
            FROM Clase c, Entrenador e
            WHERE c.idEntrenador = e.id
              AND e.id = :idEntrenador AND c.id = :idClase
            """)
    List<ClaseDetalle> listarDetalle(@Param("idEntrenador") Integer idEntrenador,
                                     @Param("idClase") Integer idClase);
}