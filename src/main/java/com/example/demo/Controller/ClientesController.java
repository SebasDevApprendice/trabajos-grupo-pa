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


    @GetMapping("/cliente/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new ClientesModel()); // <-- ¡esto evita el error!
        return "login"; // nombre del archivo HTML en templates/
    }
    @GetMapping("/registroCliente")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new ClientesModel());
        return "registroCliente"; // Vista HTML con el formulario
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute ClientesModel cliente) {
        clienteRepository.save(cliente);
        return "redirect:/login";
    }

    @GetMapping("/loginCliente")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("cliente", new ClientesModel());
        return "login";
    }

    @PostMapping("/loginCliente")
    public String loginCliente(@ModelAttribute ClientesModel cliente, Model model) {
        ClientesModel clienteEnDB = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteEnDB != null && clienteEnDB.getContrasena().equals(cliente.getContrasena())) {
            model.addAttribute("nombreCliente", clienteEnDB.getNombre());
            return "clienteBienvenido";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "loginCliente";
        }
    }
}