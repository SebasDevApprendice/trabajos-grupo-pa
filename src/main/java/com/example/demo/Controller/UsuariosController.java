package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.UsuarioRepository;

@Controller
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/formularioUsuarios")
    public String mostrarFormularioYListado(@RequestParam(required = false) Long id, Model model) {
    UsuarioModel usuario;
    if (id != null) {
        usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    } else {
        usuario = new UsuarioModel();  // Crear un nuevo usuario vacío
    }

    model.addAttribute("usuario", usuario);
    List<UsuarioModel> usuarios = usuarioRepository.findAll();
    model.addAttribute("usuarios", usuarios);
    return "formularioUsuarios";
    }

    @PostMapping("/agregarUsuario")
    public String agregarUsuario(@ModelAttribute UsuarioModel usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/formularioUsuarios";}

    @GetMapping("/formularioEdit2/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "redirect:/formularioUsuarios";}
    
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
