package com.example.demo.Controller;

import com.example.demo.Model.ProductoModel;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AgregarCarritoController {
    private ProductoRepository productoRepository;

    @GetMapping("/agregarCarrito")
    public String mostrarAgregarCarrito(@RequestParam(required = false) Long productoId, Model model) {
    if (productoId == null) {
        model.addAttribute("error", "No se proporcionó un producto válido.");
        return "agregarCarrito";
    }

    ProductoModel producto = productoRepository.findById(productoId).orElse(null);
    
    if (producto == null) {
        model.addAttribute("error", "El producto no fue encontrado.");
    } else {
        model.addAttribute("producto", producto);
    }return "/agregarCarrito";
    }
}