package com.example.gym.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gym.modelo.Cliente;
import com.example.gym.modelo.Matricula;
import com.example.gym.modelo.Plan;

@Service
public class GimnasioServicio {

    private final List<Plan> planes = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Matricula> matriculas = new ArrayList<>();

    private int siguienteIdPlan = 1;
    private int siguienteIdCliente = 1;
    private int siguienteIdMatricula = 1;

    // Datos de ejemplo para que el catálogo y las gráficas no salgan vacíos
    public GimnasioServicio() {
        agregarPlan(new Plan(0, "Plan Básico", "Acceso a zona de pesas y cardio", 79.0, 1));
        agregarPlan(new Plan(0, "Plan Estándar", "Básico + clases grupales", 129.0, 3));
        agregarPlan(new Plan(0, "Plan Premium", "Estándar + rutina con entrenador", 199.0, 6));
        agregarPlan(new Plan(0, "Plan Anual", "Acceso total durante todo el año", 349.0, 12));
        agregarPlan(new Plan(0, "Plan Estudiante", "Descuento con carné universitario", 59.0, 1));
        agregarPlan(new Plan(0, "Plan Pareja", "Dos personas, un solo pago", 219.0, 3));
        agregarPlan(new Plan(0, "Plan Semestral", "Acceso total por seis meses", 289.0, 6));
        agregarPlan(new Plan(0, "Plan Funcional", "Clases funcionales y de baile", 99.0, 1));

        agregarCliente(new Cliente(0, "Luis", "Pérez", "70000001", "999111222", "2000-05-12"));
        agregarCliente(new Cliente(0, "María", "Gómez", "70000002", "999333444", "1998-11-03"));
        agregarCliente(new Cliente(0, "Carlos", "Ramos", "70000003", "999555666", "2001-02-20"));

        agregarMatricula(new Matricula(0, 1, 1, "2026-08-05", "2026-09-05"));
        agregarMatricula(new Matricula(0, 2, 2, "2026-09-10", "2026-12-10"));
        agregarMatricula(new Matricula(0, 3, 2, "2026-09-18", "2026-12-18"));
        agregarMatricula(new Matricula(0, 1, 3, "2026-10-01", "2027-04-01"));
    }

    // ---------- PLANES ----------
    public void agregarPlan(Plan plan) {
        plan.setId(siguienteIdPlan++);
        planes.add(plan);
    }

    public List<Plan> listarPlanes() {
        return planes;
    }

    public Plan consultarPlan(int id) {
        return planes.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void eliminarPlan(int id) {
        planes.removeIf(p -> p.getId() == id);
    }

    public List<Plan> buscarPlanes(String texto) {
        String t = texto.toLowerCase();
        return planes.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(t))
                .toList();
    }

    // ---------- CLIENTES ----------
    public void agregarCliente(Cliente cliente) {
        cliente.setId(siguienteIdCliente++);
        clientes.add(cliente);
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }

    public Cliente consultarCliente(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public void eliminarCliente(int id) {
        clientes.removeIf(c -> c.getId() == id);
    }

    public List<Cliente> buscarClientes(String texto) {
        String t = texto.toLowerCase();
        return clientes.stream()
                .filter(c -> c.getNombres().toLowerCase().contains(t)
                        || c.getApellidos().toLowerCase().contains(t)
                        || c.getDni().contains(t))
                .toList();
    }

    // ---------- MATRÍCULAS ----------
    public void agregarMatricula(Matricula matricula) {
        matricula.setId(siguienteIdMatricula++);
        matriculas.add(matricula);
    }

    public List<Matricula> listarMatriculas() {
        return matriculas;
    }

    public Matricula consultarMatricula(int id) {
        return matriculas.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    public void eliminarMatricula(int id) {
        matriculas.removeIf(m -> m.getId() == id);
    }

    public List<Matricula> buscarMatriculasPorCliente(int idCliente) {
        return matriculas.stream()
                .filter(m -> m.getIdCliente() != null && m.getIdCliente() == idCliente)
                .toList();
    }
}