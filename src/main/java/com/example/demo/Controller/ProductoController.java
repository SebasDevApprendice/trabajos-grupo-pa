package com.example.demo.Controller;

import com.example.demo.Model.CategoriaEnum;
import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.TallaEnum;
import com.example.demo.Repository.ProductoRepository;

import jakarta.servlet.http.HttpSession;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoController extends SessionController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/formularioRopa")
    public String mostrarFormularioYListado(@RequestParam(required = false) Long codigo, Model model,
            HttpSession session) {

        if (!sesionAdminOAsesor(session, model)) {
            return "redirect:/login";
        }

        ProductoModel producto;
        if (codigo != null) {
            producto = productoRepository.findById(codigo).orElse(new ProductoModel());
        } else {
            producto = new ProductoModel();
        }

        model.addAttribute("producto", producto);
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("tallas", TallaEnum.values());
        model.addAttribute("categorias", CategoriaEnum.values());
        return "formularioRopa";
    }

    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute ProductoModel producto) {
        productoRepository.save(producto);
        return "redirect:/formularioRopa";
    }

    @GetMapping("/formularioEdit/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ProductoModel producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "formularioEdit";
    }

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

    @GetMapping("/reponer")
    public String mostrarProductosBajos(Model model, HttpSession session) {
        if (!sesionAdminOAsesor(session, model)) {
            return "redirect:/login";
        }
        List<ProductoModel> productosBajos = productoRepository.findByCantidadLessThan(5);
        Queue<ProductoModel> colaProductos = new LinkedList<>(productosBajos);
        model.addAttribute("colaProductos", colaProductos);
        return "reponer";
    }

    @PostMapping("/reponer")
    public String reponerStock(@RequestParam("id") Long id, @RequestParam("cantidadAdicional") int cantidadAdicional,
            HttpSession session, Model model) {
        if (!sesionAdminOAsesor(session, model)) {
            return "redirect:/login";
        }
        ProductoModel producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setCantidad(producto.getCantidad() + cantidadAdicional);
        productoRepository.save(producto);
        return "redirect:/reponer";
    }
}
