package com.example.demo.Controller;

import com.example.demo.Model.ProductoModel;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {
        List<ProductoModel> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "inventario"; 
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new ProductoModel()); 
        return "agregarProducto"; 
    }

    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute ProductoModel producto) {
        productoRepository.save(producto); 
        return "redirect:/producto/inventario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ProductoModel producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "editarProducto";}
    
@PostMapping("/editar")
    public String editarProducto(@ModelAttribute ProductoModel producto) {
        ProductoModel productoExistente = productoRepository.findById(producto.getCodigo()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setCantidad(producto.getCantidad());
        productoExistente.setTalla(producto.getTalla());
        productoRepository.save(productoExistente);
        return "redirect:/producto/inventario";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/producto/inventario";
    }

}