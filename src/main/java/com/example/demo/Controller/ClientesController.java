package com.example.demo.Controller;

import com.example.demo.Model.ClientesModel;
import com.example.demo.Repository.ClientesRepository;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientesController extends SessionController {

    @Autowired
    private ClientesRepository clienteRepository;


    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute("cliente") ClientesModel cliente, Model model) {
        
        LocalDate fechaNacimiento = cliente.getFecha_nac().toLocalDate();
        if (!fechaNacimiento.isBefore(LocalDate.now())) {
        model.addAttribute("error", "La fecha de nacimiento no puede ser hoy ni futura.");
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
        agregarClienteAModel(session, model);
        return "Menu_Inicio";
    }

    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        agregarClienteAModel(session, model);
        return "carrito";
    }

    @GetMapping("/vistaAdmin")
    public String vistaAdmin(HttpSession session, Model model) {
        agregarClienteAModel(session, model);
        return "vistaAdmin";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/Menu_Inicio"; 
}
}
