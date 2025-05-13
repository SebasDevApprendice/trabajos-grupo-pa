package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavPagController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/Menu_Inicio")
    public String Menu_Inicio() {
        return "Menu_Inicio";
    }

    @RequestMapping("/registro")
    public String registro() {
        return "registro";
    }

    @RequestMapping("/carrito")
    public String carrito() {
        return "carrito";
    }

    @RequestMapping("/vistaAdmin")
    public String vistaAdmin() {
        return "vistaAdmin";
    }

    @RequestMapping("/formularioRopa")
    public String formulario() {
        return "formulario";
    }

    @RequestMapping("/formularioUsuarios")
    public String formulario2() {
        return "formularioUsuarios";
    }

}