package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Model.ProductoModel;
import com.example.demo.Repository.ProductoRepository;

@Controller
public class CatalogoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        List<ProductoModel> productos = productoRepository.findAll(); // << Aquí usas el repo directo
        model.addAttribute("productos", productos);
        return "catalogo";
    }
}