package com.example.demo.Controller;

import com.example.demo.Model.CarritoModel;
import com.example.demo.Model.CarritoProductoModel;
import com.example.demo.Model.ClientesModel;
import com.example.demo.Model.DetalleVentaModel;
import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.VentaModel;
import com.example.demo.Repository.VentaRepository;
import com.example.demo.Repository.CarritoRepository;
import com.example.demo.Repository.ClientesRepository;
import com.example.demo.Repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VentaController extends SessionController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ClientesRepository clientesRepository;
    
    @Autowired
    private ProductoRepository productoRepository; 

    @GetMapping("/factura")
    public String mostrarFactura(Model model, HttpSession session) {
        ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
        if (cliente != null) {
            CarritoModel carrito = cliente.getCarrito();
            float total = 0;
            for (CarritoProductoModel item : carrito.getCarritoProductos()) {
                total += item.getProducto().getPrecio() * item.getCantidad();
            }
            carrito.setCostoCarrito(total);
            model.addAttribute("carrito", carrito);
        }
        return "factura";
    }

    @PostMapping("/procesar-venta")
    public String procesarVenta(HttpSession session, Model model) {
        ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
        if (cliente == null) {
            return "redirect:/login";
        }
        CarritoModel carrito = cliente.getCarrito();
        if (carrito == null || carrito.getCarritoProductos().isEmpty()) {
            return "redirect:/carrito?error=El+carrito+está+vacío";
        }

        VentaModel venta = new VentaModel();
        venta.setCliente(cliente);
        venta.setFechaVenta(new java.sql.Date(System.currentTimeMillis()));
        List<DetalleVentaModel> detalles = new ArrayList<>();
        double totalVenta = 0;

        for (CarritoProductoModel item : carrito.getCarritoProductos()) {
            ProductoModel producto = item.getProducto();
            int stockActual = producto.getCantidad();
            int cantidadVendida = item.getCantidad();

            if (stockActual < cantidadVendida) {
                model.addAttribute("error", "Stock insuficiente para el producto: " + producto.getNombre());
                model.addAttribute("carrito", carrito);
                return "factura";
            }
            
            producto.setCantidad(stockActual - cantidadVendida);
            productoRepository.save(producto);
            double subtotal = producto.getPrecio() * cantidadVendida;
            totalVenta += subtotal;
            DetalleVentaModel detalle = new DetalleVentaModel();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidadVendida);
            detalle.setSubtotal(subtotal);
            detalles.add(detalle);
        }
        venta.setTotal(totalVenta);
        venta.setDetalles(detalles);

        if (cliente.getSaldo() < totalVenta) {
            model.addAttribute("error", "Saldo insuficiente para completar la compra.");
            model.addAttribute("carrito", carrito);
            return "factura";
        }

        cliente.setSaldo(cliente.getSaldo() - totalVenta);
        clientesRepository.save(cliente);
        ventaRepository.save(venta);
        carrito.getCarritoProductos().clear();
        carrito.setCostoCarrito(0);
        carritoRepository.save(carrito);
        model.addAttribute("venta", venta);
        return "facturaCompleta";
    }
}