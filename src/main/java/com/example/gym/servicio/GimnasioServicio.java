package com.example.gym.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gym.modelo.Cliente;
import com.example.gym.modelo.Entrenador;
import com.example.gym.modelo.Matricula;
import com.example.gym.modelo.Plan;
import com.example.gym.repositorio.ClienteRepositorio;
import com.example.gym.repositorio.EntrenadorRepositorio;
import com.example.gym.repositorio.MatriculaRepositorio;
import com.example.gym.repositorio.PlanRepositorio;

@Service
public class GimnasioServicio {

    private final PlanRepositorio planRepo;
    private final ClienteRepositorio clienteRepo;
    private final MatriculaRepositorio matriculaRepo;
    private final EntrenadorRepositorio entrenadorRepo;

    public GimnasioServicio(PlanRepositorio planRepo, ClienteRepositorio clienteRepo,
                            MatriculaRepositorio matriculaRepo, EntrenadorRepositorio entrenadorRepo) {
        this.planRepo = planRepo;
        this.clienteRepo = clienteRepo;
        this.matriculaRepo = matriculaRepo;
        this.entrenadorRepo = entrenadorRepo;
    }

    // ---------- PLANES ----------
    public void agregarPlan(Plan plan) {
        planRepo.save(plan);
    }

    public List<Plan> listarPlanes() {
        return planRepo.findAll();
    }

    public Plan consultarPlan(int id) {
        return planRepo.findById(id).orElse(null);
    }

    public void eliminarPlan(int id) {
        planRepo.deleteById(id);
    }

    public List<Plan> buscarPlanes(String texto) {
        return planRepo.findByNombreContainingIgnoreCase(texto);
    }

    // ---------- CLIENTES ----------
    public void agregarCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    public Cliente consultarCliente(int id) {
        return clienteRepo.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarCliente(int id) {
        matriculaRepo.deleteByIdCliente(id);   // primero sus matrículas, por la llave foránea
        clienteRepo.deleteById(id);
    }

    public List<Cliente> buscarClientes(String texto) {
        return clienteRepo
                .findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrDniContaining(
                        texto, texto, texto);
    }

    // ---------- MATRÍCULAS ----------
    public void agregarMatricula(Matricula matricula) {
        matriculaRepo.save(matricula);
    }

    public List<Matricula> listarMatriculas() {
        return matriculaRepo.findAll();
    }

    public Matricula consultarMatricula(int id) {
        return matriculaRepo.findById(id).orElse(null);
    }

    public void eliminarMatricula(int id) {
        matriculaRepo.deleteById(id);
    }

    public List<Matricula> buscarMatriculasPorCliente(int idCliente) {
        return matriculaRepo.findByIdCliente(idCliente);
    }

    // ---------- ENTRENADORES ----------
    public List<Entrenador> listarEntrenadores() {
        return entrenadorRepo.findAll();
    }
}