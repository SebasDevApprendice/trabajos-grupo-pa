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
    private UsuarioRepository usuarioRepository;

    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuarioLogin", new UsuarioLoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("usuarioLogin") UsuarioLoginDto usuarioLogin,
                        Model model,
                        HttpSession session) {
        String email = usuarioLogin.getEmail();
        String contrasena = usuarioLogin.getContrasena();

        // Verificar cliente
        ClientesModel cliente = clientesRepository.findByEmail(email);
        if (cliente != null && cliente.getContrasena().equals(contrasena)) {
            session.setAttribute("clienteLogueado", cliente);
            return "redirect:/Menu_Inicio";
        }

        // Verificar usuario
        UsuarioModel usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            session.setAttribute("usuarioLogueado", usuario);

            // Redirigir según tipo
            TipoUsuario tipo = usuario.getUsrTipo();
            if (tipo == TipoUsuario.Administrador || tipo == TipoUsuario.Asesor) {
                return "redirect:/vistaAdmin";
            }
        }

        // Verificar admin quemado
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(contrasena)) {
        UsuarioModel admin = new UsuarioModel();
        admin.setEmail(ADMIN_EMAIL);
        admin.setNombre("Admin Predeterminado");
        admin.setUsrTipo(TipoUsuario.Administrador);

        session.setAttribute("usuarioLogueado", admin);
        return "redirect:/vistaAdmin";
    }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    @GetMapping("/vistaAdmin")
    public String vistaAdmin(HttpSession session, Model model) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogueado");

    if (usuario == null || 
        (!usuario.getUsrTipo().name().equals("Administrador") && 
        !usuario.getUsrTipo().name().equals("Asesor"))) {
        return "redirect:/login";
    }

    model.addAttribute("usuarioLogueado", usuario);
    return "vistaAdmin";
}

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

