package com.example.gym.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gym.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrDniContaining(
            String nombres, String apellidos, String dni);
}