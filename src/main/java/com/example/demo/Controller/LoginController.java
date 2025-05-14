package com.example.demo.Controller;

import com.example.demo.DTO.UsuarioLoginDto;
import com.example.demo.Model.ClientesModel;
import com.example.demo.Model.TipoUsuario;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.ClientesRepository;
import com.example.demo.Repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private UsuarioRepository UsuarioRepository;

    
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin123"; 

    @GetMapping("/login")
    public String mostrarFormularioLogin(HttpSession session, Model model) {
        model.addAttribute("usuarioLogin", new UsuarioLoginDto());
        return "login"; 
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("usuarioLogin") UsuarioLoginDto usuarioLogin, Model model, HttpSession session) {
        String email = usuarioLogin.getEmail();
        String contrasena = usuarioLogin.getContrasena();

        ClientesModel cliente = clientesRepository.findByEmail(email);
        if (cliente != null && cliente.getContrasena().equals(cliente.getContrasena())) {
            session.setAttribute("clienteLogueado", cliente);
            return "redirect:/Menu_Inicio"; 
        }

        
        UsuarioModel usuario = UsuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            TipoUsuario tipo = usuario.getUsrTipo();
            if (tipo == TipoUsuario.Administrador) {
                return "vistaAdmin";
            } else if (tipo == TipoUsuario.Asesor) {
                return "vistaAdmin";
            }
        }

        
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(contrasena)) {
            return "vistaAdmin"; 
        }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }
}

