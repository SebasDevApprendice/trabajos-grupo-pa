package com.example.demo.Controller;

import com.example.demo.Model.ClientesModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public abstract class SessionController {

    protected void agregarClienteAModel(HttpSession session, Model model) {
        ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
        model.addAttribute("cliente", cliente);
    }
}