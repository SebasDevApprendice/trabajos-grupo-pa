package com.example.demo.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.example.demo.Model.TipoUsuario;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuariosController extends SessionController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/formularioUsuarios")
    public String mostrarFormularioYListado(@RequestParam(required = false) Long id, Model model, HttpSession session) {

        if (!sesionAdminOAsesor(session, model)) {
        return "redirect:/login";
    }

        UsuarioModel usuario;
        if (id != null) {
            usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        } else {
            usuario = new UsuarioModel();
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("tiposUsuario", TipoUsuario.values());

        model.addAttribute("fechaMaxima", LocalDate.now().minusDays(1).toString());

        return "formularioUsuarios";
    }

    @PostMapping("/agregarUsuario")
    public String agregarUsuario(@ModelAttribute UsuarioModel usuario, Model model, HttpSession session) {

        if (!sesionAdminOAsesor(session, model)) {
        return "redirect:/login";
    }
    
    UsuarioModel existente = usuarioRepository.findByEmail(usuario.getEmail());

    
    if (existente != null && existente.getId() != usuario.getId()) {
        model.addAttribute("errorEmail", "⚠️ Ya existe un usuario registrado con ese correo.");
        model.addAttribute("usuario", usuario); // mantiene los datos llenos
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("tiposUsuario", TipoUsuario.values());
        model.addAttribute("fechaMaxima", LocalDate.now().minusDays(1).toString());
        return "formularioUsuarios";
    }

    
    usuarioRepository.save(usuario);
    return "redirect:/formularioUsuarios";
    }

    @GetMapping("/formularioEdit2/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "redirect:/formularioUsuarios";
    }

    @PostMapping("/editarUsuario")
    public String editarUsuario(@ModelAttribute UsuarioModel usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/formularioUsuarios";
    }

    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/formularioUsuarios";
    }
}
