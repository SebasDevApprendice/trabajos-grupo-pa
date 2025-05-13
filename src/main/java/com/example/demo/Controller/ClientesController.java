package com.example.demo.Controller;

import com.example.demo.Model.ClientesModel;
import com.example.demo.Repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ClientesController {

    @Autowired
    private ClientesRepository clienteRepository;

    
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("cliente", new ClientesModel());
        return "login"; 
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute("cliente") ClientesModel cliente) {
        clienteRepository.save(cliente);
        return "redirect:/login";
    }

    @PostMapping("/loginCliente")
    public String loginCliente(@ModelAttribute("cliente") ClientesModel cliente, Model model) {
        ClientesModel clienteEnDB = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteEnDB != null && clienteEnDB.getContrasena().equals(cliente.getContrasena())) {
            model.addAttribute("nombreCliente", clienteEnDB.getNombre());
            return "clienteBienvenido";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            model.addAttribute("cliente", new ClientesModel()); 
            return "login";
        }
    }
}
