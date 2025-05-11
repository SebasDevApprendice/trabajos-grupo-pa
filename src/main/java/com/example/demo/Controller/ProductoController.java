package com.example.demo.Controller;

import com.example.demo.Model.ProductoModel;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/formulario")
public String mostrarFormularioYListado(Model model) {
    model.addAttribute("producto", new ProductoModel());
    List<ProductoModel> productos = productoRepository.findAll();
    model.addAttribute("productos", productos);
    return "formulario";}

    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute ProductoModel producto) {
        productoRepository.save(producto);
        return "redirect:/formulario";}

    @GetMapping("/formularioEdit/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ProductoModel producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "formularioEdit";}
    
@PostMapping("/editar")
    public String editarProducto(@ModelAttribute ProductoModel producto) {
        ProductoModel productoExistente = productoRepository.findById(producto.getCodigo()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setCantidad(producto.getCantidad());
        productoExistente.setTalla(producto.getTalla());
        productoRepository.save(productoExistente);
        return "redirect:/formulario";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/formulario";
    }

}