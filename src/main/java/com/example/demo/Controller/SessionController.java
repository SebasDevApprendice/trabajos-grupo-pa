package com.example.demo.Controller;

import com.example.demo.Model.ClientesModel;
import com.example.demo.Model.UsuarioModel;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public abstract class SessionController {

    protected void agregarSesion(HttpSession session, Model model) {
    ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
    model.addAttribute("cliente", cliente);

    UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogueado");
    model.addAttribute("usuarioLogueado", usuario);
    }

    protected boolean sesionAdminOAsesor(HttpSession session, Model model) {
        agregarSesion(session, model);
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogueado");

        return usuario != null &&
                (usuario.getUsrTipo().name().equals("Administrador") ||
                usuario.getUsrTipo().name().equals("Asesor"));
    }
    
}