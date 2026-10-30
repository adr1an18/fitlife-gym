package com.example.gym.servicio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gym.dto.ClaseDetalle;
import com.example.gym.dto.MatriculaDetalle;
import com.example.gym.modelo.Clase;
import com.example.gym.modelo.Cliente;
import com.example.gym.modelo.Entrenador;
import com.example.gym.modelo.Matricula;
import com.example.gym.modelo.Plan;
import com.example.gym.repositorio.ClaseRepositorio;
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
    private final ClaseRepositorio claseRepo;

    public GimnasioServicio(PlanRepositorio planRepo,
                            ClienteRepositorio clienteRepo,
                            MatriculaRepositorio matriculaRepo,
                            EntrenadorRepositorio entrenadorRepo,
                            ClaseRepositorio claseRepo) {
        this.planRepo = planRepo;
        this.clienteRepo = clienteRepo;
        this.matriculaRepo = matriculaRepo;
        this.entrenadorRepo = entrenadorRepo;
        this.claseRepo = claseRepo;
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
        // No borramos sus matrículas, rutinas ni asistencias.
        // Las llaves foráneas impiden eliminar clientes con historial.
        clienteRepo.deleteById(id);

        // Ejecuta el borrado dentro de esta transacción para detectar
        // inmediatamente cualquier restricción de la base de datos.
        clienteRepo.flush();
    }

    public List<Cliente> buscarClientes(String texto) {
        return clienteRepo
                .findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrDniContaining(
                        texto, texto, texto);
    }

    // ---------- MATRÍCULAS ----------
    public void agregarMatricula(Matricula matricula) {
        Plan plan = planRepo.findById(matricula.getIdPlan()).orElse(null);

        if (plan != null && matricula.getFechaInicio() != null) {
            LocalDate fin = LocalDate.parse(matricula.getFechaInicio())
                    .plusMonths(plan.getDuracionMeses());

            matricula.setFechaFin(fin.toString());
        }

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

    public List<MatriculaDetalle> listarDetalleMatriculas() {
        return matriculaRepo.listarDetalle();
    }

    // ---------- CONSULTA: PLAN -> CLIENTE ----------
    public List<Cliente> listarClientesPorPlan(Integer idPlan) {
        return clienteRepo.listarPorPlan(idPlan);
    }

    public List<MatriculaDetalle> listarDetallePorPlanYCliente(
            Integer idPlan, Integer idCliente) {
        return matriculaRepo.listarDetallePorPlanYCliente(idPlan, idCliente);
    }

    // ---------- ENTRENADORES ----------
    public List<Entrenador> listarEntrenadores() {
        return entrenadorRepo.findAll();
    }

    // ---------- CONSULTA: ENTRENADOR -> CLASE ----------
    public List<Clase> listarClasesPorEntrenador(Integer idEntrenador) {
        return claseRepo.findByIdEntrenador(idEntrenador);
    }

    public List<ClaseDetalle> listarDetalleClase(
            Integer idEntrenador, Integer idClase) {
        return claseRepo.listarDetalle(idEntrenador, idClase);
    }
}