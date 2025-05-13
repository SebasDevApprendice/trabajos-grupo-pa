package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavPagController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
        @RequestMapping("/registroCl")
    public String registroCl() {
        return "registroCl";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/Menu_Inicio")
    public String Menu_Inicio() {
        return "Menu_Inicio";
    }

    @RequestMapping("/carrito")
    public String carrito() {
        return "carrito";
    }

    @RequestMapping("/vistaAdmin")
    public String vistaAdmin() {
        return "vistaAdmin";
    }
        @RequestMapping("/Menu_cliente")
    public String Menu_cliente() {
        return "Menu_cliente";
    }

    @RequestMapping("/formularioRopa")
    public String formulario() {
        return "formulario";
    }

    @RequestMapping("/formularioUsuarios")
    public String formulario2() {
        return "formularioUsuarios";
    }
    @RequestMapping("/catalogo")
    public String caralogo() {
        return "catalogo";
    }

}