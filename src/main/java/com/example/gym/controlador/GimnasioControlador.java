package com.example.gym.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.gym.modelo.Cliente;
import com.example.gym.modelo.Matricula;
import com.example.gym.modelo.Plan;
import com.example.gym.servicio.GimnasioServicio;

@Controller
public class GimnasioControlador {

    private final GimnasioServicio servicio;

    public GimnasioControlador(GimnasioServicio servicio) {
        this.servicio = servicio;
    }

    // ---------- PÁGINAS ----------
    @GetMapping("/")
    public String inicio(Model modelo) {
        modelo.addAttribute("planes", servicio.listarPlanes());
        return "inicio";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model modelo) {
        modelo.addAttribute("planes", servicio.listarPlanes());
        return "catalogo";
    }

    @GetMapping("/nosotros")
    public String nosotros(Model modelo) {
        modelo.addAttribute("entrenadores", servicio.listarEntrenadores());
        return "nosotros";
    }

    @GetMapping("/matriculas")
    public String matriculas(Model modelo) {
        cargarDatosMatriculas(modelo);
        modelo.addAttribute("clientes", servicio.listarClientes());
        return "matriculas";
    }

    // ---------- GRÁFICAS ----------
    @GetMapping("/graficas/planes")
    public String graficaPlanes(Model modelo) {
        List<Plan> planes = servicio.listarPlanes();

        modelo.addAttribute(
                "nombres",
                planes.stream().map(Plan::getNombre).toList());

        modelo.addAttribute(
                "precios",
                planes.stream().map(Plan::getPrecio).toList());

        modelo.addAttribute(
                "duraciones",
                planes.stream().map(Plan::getDuracionMeses).toList());

        return "graficaplanes";
    }

    @GetMapping("/graficas/matriculas")
    public String graficaMatriculas(Model modelo) {
        List<Plan> planes = servicio.listarPlanes();
        List<Matricula> matriculas = servicio.listarMatriculas();

        List<String> nombresPlanes = new ArrayList<>();
        List<Long> cantidadPorPlan = new ArrayList<>();

        for (Plan p : planes) {
            nombresPlanes.add(p.getNombre());

            long cantidad = matriculas.stream()
                    .filter(m -> m.getIdPlan() != null
                            && m.getIdPlan() == p.getId())
                    .count();

            cantidadPorPlan.add(cantidad);
        }

        Map<String, Long> porMes = new TreeMap<>();

        for (Matricula m : matriculas) {
            String fecha = m.getFechaInicio();

            if (fecha != null && fecha.length() >= 7) {
                porMes.merge(fecha.substring(0, 7), 1L, Long::sum);
            }
        }

        modelo.addAttribute("nombresPlanes", nombresPlanes);
        modelo.addAttribute("cantidadPorPlan", cantidadPorPlan);
        modelo.addAttribute("meses", new ArrayList<>(porMes.keySet()));
        modelo.addAttribute("cantidadPorMes", new ArrayList<>(porMes.values()));

        return "graficamatriculas";
    }

    // ---------- CLIENTES ----------
    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        servicio.agregarCliente(cliente);
        return "redirect:/matriculas";
    }

    @GetMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(
            @PathVariable int id,
            RedirectAttributes redirect) {

        if (servicio.consultarCliente(id) == null) {
            redirect.addFlashAttribute(
                    "errorCliente",
                    "El cliente que intentas eliminar ya no existe.");

            return "redirect:/matriculas";
        }

        try {
            servicio.eliminarCliente(id);

            redirect.addFlashAttribute(
                    "exitoCliente",
                    "Cliente eliminado correctamente.");

        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute(
                    "errorCliente",
                    "No se puede eliminar este cliente porque tiene "
                            + "matrículas, rutinas o asistencias registradas. "
                            + "Su historial se conserva.");
        }

        return "redirect:/matriculas";
    }

    @GetMapping("/clientes/buscar")
    public String buscarClientes(
            @RequestParam String texto,
            Model modelo) {

        cargarDatosMatriculas(modelo);
        modelo.addAttribute("clientes", servicio.buscarClientes(texto));

        return "matriculas";
    }

    @GetMapping("/clientes/editar/{id}")
    public String editarCliente(@PathVariable int id, Model modelo) {
        cargarDatosMatriculas(modelo);

        Cliente cliente = servicio.consultarCliente(id);

        if (cliente != null) {
            modelo.addAttribute("cliente", cliente);
        }

        modelo.addAttribute("clientes", servicio.listarClientes());

        return "matriculas";
    }

    // ---------- MATRÍCULAS ----------
    @PostMapping("/matriculas/guardar")
    public String guardarMatricula(@ModelAttribute Matricula matricula) {
        servicio.agregarMatricula(matricula);
        return "redirect:/matriculas";
    }

    @GetMapping("/matriculas/eliminar/{id}")
    public String eliminarMatricula(@PathVariable int id) {
        servicio.eliminarMatricula(id);
        return "redirect:/matriculas";
    }

    // ---------- CONSULTA EN CASCADA: PLAN -> CLIENTE ----------
    @GetMapping("/consultas/plan-cliente")
    public String consultaPlanCliente(
            @RequestParam(required = false) Integer idPlan,
            @RequestParam(required = false) Integer idCliente,
            Model modelo) {

        modelo.addAttribute("planes", servicio.listarPlanes());
        modelo.addAttribute("idPlan", idPlan);
        modelo.addAttribute("idCliente", idCliente);

        if (idPlan != null) {
            modelo.addAttribute(
                    "clientes",
                    servicio.listarClientesPorPlan(idPlan));

            if (idCliente != null) {
                modelo.addAttribute(
                        "detalle",
                        servicio.listarDetallePorPlanYCliente(
                                idPlan, idCliente));
            }
        }

        return "consultaplancliente";
    }

    // ---------- PLANES (CRUD) ----------
    @GetMapping("/planes")
    public String planes(Model modelo) {
        modelo.addAttribute("plan", new Plan());
        modelo.addAttribute("planes", servicio.listarPlanes());

        return "planes";
    }

    @GetMapping("/planes/editar/{id}")
    public String editarPlan(@PathVariable int id, Model modelo) {
        Plan plan = servicio.consultarPlan(id);

        modelo.addAttribute("plan", plan != null ? plan : new Plan());
        modelo.addAttribute("planes", servicio.listarPlanes());

        return "planes";
    }

    @PostMapping("/planes/guardar")
    public String guardarPlan(@ModelAttribute Plan plan) {
        servicio.agregarPlan(plan);
        return "redirect:/planes";
    }

    @GetMapping("/planes/eliminar/{id}")
    public String eliminarPlan(
            @PathVariable int id,
            RedirectAttributes redirect) {

        try {
            servicio.eliminarPlan(id);

        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute(
                    "error",
                    "No se puede eliminar: el plan tiene matrículas.");
        }

        return "redirect:/planes";
    }

    // ---------- CONSULTA EN CASCADA: ENTRENADOR -> CLASE ----------
    @GetMapping("/consultas/entrenador-clase")
    public String consultaEntrenadorClase(
            @RequestParam(required = false) Integer idEntrenador,
            @RequestParam(required = false) Integer idClase,
            Model modelo) {

        modelo.addAttribute(
                "entrenadores",
                servicio.listarEntrenadores());

        modelo.addAttribute("idEntrenador", idEntrenador);
        modelo.addAttribute("idClase", idClase);

        if (idEntrenador != null) {
            modelo.addAttribute(
                    "clases",
                    servicio.listarClasesPorEntrenador(idEntrenador));

            if (idClase != null) {
                modelo.addAttribute(
                        "detalle",
                        servicio.listarDetalleClase(
                                idEntrenador, idClase));
            }
        }

        return "consultaentrenadorclase";
    }

    // ---------- AUXILIAR ----------
    private void cargarDatosMatriculas(Model modelo) {
        modelo.addAttribute("cliente", new Cliente());
        modelo.addAttribute("matricula", new Matricula());
        modelo.addAttribute("planes", servicio.listarPlanes());
        modelo.addAttribute(
                "matriculas",
                servicio.listarDetalleMatriculas());
    }
}