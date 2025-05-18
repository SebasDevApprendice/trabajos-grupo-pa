package com.example.demo.Controller;

import com.example.demo.Model.ClientesModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @ModelAttribute
    public void agregarClienteLogueadoAlModelo(Model model, HttpSession session) {
        ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
        if (cliente != null) {
            model.addAttribute("clienteLogueado", cliente);
        }
    }
}
