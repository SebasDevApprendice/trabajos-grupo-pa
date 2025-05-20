package com.example.demo.Controller;

import com.example.demo.DTO.ProductoDto;
import com.example.demo.Model.ClientesModel;
import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.TallaEnum;
import com.example.demo.Repository.ClientesRepository;
import com.example.demo.Repository.ProductoRepository;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientesController extends SessionController {

    @Autowired
    private ClientesRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute("cliente") ClientesModel cliente, Model model) {
        
        LocalDate fechaNacimiento = cliente.getFecha_nac().toLocalDate();

    if (!fechaNacimiento.isBefore(LocalDate.now())) {
        model.addAttribute("error", "La fecha de nacimiento no puede ser hoy ni futura.");
        model.addAttribute("fechaMaxima", LocalDate.now().minusDays(1).toString());
        return "registroCl";
    }

    if (clienteRepository.existsByEmail(cliente.getEmail())) {
        model.addAttribute("error", "El correo ya está registrado.");
        model.addAttribute("fechaMaxima", LocalDate.now().minusDays(1).toString());
        return "registroCl";
    }

    cliente.setRol("CLIENTE");

    double saldo = Math.random() * 250000 + 50000;
    saldo = Math.round(saldo * 100.0) / 100.0;

    cliente.setSaldo(saldo);
    clienteRepository.save(cliente);
    
    return "redirect:/login";
}


    @GetMapping("/registroCl")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new ClientesModel());

        LocalDate ayer = LocalDate.now().minusDays(1);
        model.addAttribute("fechaMaxima", ayer.toString()); // formatea yyyy-MM-dd

        return "registroCl";
    }

    @GetMapping("/Menu_Inicio")
    public String menuInicio(HttpSession session, Model model) {
        
        System.out.println("Cliente en sesión: " + session.getAttribute("clienteLogueado"));
        System.out.println("Usuario en sesión: " + session.getAttribute("usuarioLogueado"));

        agregarSesion(session, model);

        if (session.getAttribute("clienteLogueado") == null && session.getAttribute("usuarioLogueado") == null) {
        return "redirect:/login";
        }

        // Obtener productos y agruparlos para el catálogo
        List<ProductoModel> todos = productoRepository.findAll();

        Map<String, List<ProductoModel>> agrupados = todos.stream()
            .collect(Collectors.groupingBy(ProductoModel::getNombre));

        List<ProductoDto> productosCatalogo = new ArrayList<>();

        for (Map.Entry<String, List<ProductoModel>> entry : agrupados.entrySet()) {
            ProductoModel base = entry.getValue().get(0);
            List<TallaEnum> tallas = entry.getValue().stream()
                .map(ProductoModel::getTalla)
                .distinct()
                .collect(Collectors.toList());

            productosCatalogo.add(new ProductoDto(base, tallas));
        }

        model.addAttribute("productos", productosCatalogo);

        return "Menu_Inicio";
    }

    
}
