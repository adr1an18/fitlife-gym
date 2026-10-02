package com.example.gym.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String nosotros() {
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
        modelo.addAttribute("nombres", planes.stream().map(Plan::getNombre).toList());
        modelo.addAttribute("precios", planes.stream().map(Plan::getPrecio).toList());
        modelo.addAttribute("duraciones", planes.stream().map(Plan::getDuracionMeses).toList());
        return "graficaplanes";
    }

    @GetMapping("/graficas/matriculas")
    public String graficaMatriculas(Model modelo) {
        List<Plan> planes = servicio.listarPlanes();
        List<Matricula> matriculas = servicio.listarMatriculas();

        // Matrículas por plan
        List<String> nombresPlanes = new ArrayList<>();
        List<Long> cantidadPorPlan = new ArrayList<>();
        for (Plan p : planes) {
            nombresPlanes.add(p.getNombre());
            long cantidad = matriculas.stream()
                    .filter(m -> m.getIdPlan() != null && m.getIdPlan() == p.getId())
                    .count();
            cantidadPorPlan.add(cantidad);
        }

        // Matrículas por mes de inicio (aaaa-mm)
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
    public String eliminarCliente(@PathVariable int id) {
        servicio.eliminarCliente(id);
        return "redirect:/matriculas";
    }

    @GetMapping("/clientes/buscar")
    public String buscarClientes(@RequestParam String texto, Model modelo) {
        cargarDatosMatriculas(modelo);
        modelo.addAttribute("clientes", servicio.buscarClientes(texto));
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

    // ---------- AUXILIAR ----------
    private void cargarDatosMatriculas(Model modelo) {
        modelo.addAttribute("cliente", new Cliente());
        modelo.addAttribute("matricula", new Matricula());
        modelo.addAttribute("planes", servicio.listarPlanes());
        modelo.addAttribute("matriculas", servicio.listarMatriculas());
    }
}