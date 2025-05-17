package com.example.demo.Controller;

import com.example.demo.Model.CategoriaEnum;
import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.TallaEnum;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/formularioRopa")
    public String mostrarFormularioYListado(@RequestParam(required = false) Long codigo, Model model) {
    ProductoModel producto = (codigo != null)
            ? productoRepository.findById(codigo).orElse(new ProductoModel())
            : new ProductoModel();

    model.addAttribute("producto", producto);
    model.addAttribute("productos", productoRepository.findAll());
    model.addAttribute("tallas", TallaEnum.values());
    model.addAttribute("categorias", CategoriaEnum.values());
    return "formularioRopa";
}

    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute ProductoModel producto) {
        productoRepository.save(producto);
        return "redirect:/formularioRopa";}

    @GetMapping("/formularioEdit/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ProductoModel producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "formularioEdit";}

@PostMapping("/editar")
    public String editarRopa(@ModelAttribute ProductoModel producto) {
        productoRepository.save(producto);
        return "redirect:/formularioRopa";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/formularioRopa";
    }

}