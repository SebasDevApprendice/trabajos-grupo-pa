package com.example.demo.Controller;

import com.example.demo.Model.CarritoModel;
import com.example.demo.Model.CarritoProductoModel;
import com.example.demo.Model.ClientesModel;
import com.example.demo.Model.ProductoModel;
import com.example.demo.Repository.CarritoProductoRepository;
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

import java.util.Optional;

@Controller
public class CarritoController {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping("/carrito")
public String verCarrito(HttpSession session, Model model) {
    ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");

    if (cliente == null) {
        return "redirect:/login";
    }

    CarritoModel carrito = cliente.getCarrito();
    if (carrito == null) { 
        carrito = new CarritoModel();
        carrito.setCliente(cliente);
        carritoRepository.save(carrito);
        cliente.setCarrito(carrito);
        clientesRepository.save(cliente);
    }

    List<CarritoProductoModel> productosEnCarrito = carrito.getCarritoProductos();

    float total = 0;
    // Multiplica el precio por la cantidad correspondiente de cada producto
    for (CarritoProductoModel item : productosEnCarrito) {
        total += item.getProducto().getPrecio() * item.getCantidad();
    }
    carrito.setCostoCarrito(total);

    model.addAttribute("carrito", carrito);
    model.addAttribute("carritoProductos", productosEnCarrito != null ? productosEnCarrito : new ArrayList<>());
    
    return "carrito";
}

    @PostMapping("/agregar/{productoCod}")
public String agregarProductoAlCarrito(@PathVariable Long productoCod,
        @RequestParam int cantidad,
        HttpSession session) {
    ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");

    if (cliente == null) {
        return "redirect:/login";
    }

    Optional<ProductoModel> optionalProducto = productoRepository.findById(productoCod);
    if (optionalProducto.isEmpty()) {
        return "redirect:/agregarCarrito";
    }

    ProductoModel producto = optionalProducto.get();

    if (cantidad > producto.getCantidad()) {
        return "redirect:/carrito?error=No+haysuficiente+stock+para+agregar";
    }

    CarritoModel carrito = cliente.getCarrito();
    if (carrito == null) {
        carrito = new CarritoModel();
        carrito.setCliente(cliente);
        carritoRepository.save(carrito);
        cliente.setCarrito(carrito);
        clientesRepository.save(cliente);
    }

    boolean existe = false;
    for (CarritoProductoModel item : carrito.getCarritoProductos()) {
        if (item.getProducto().getCodigo().equals(productoCod)) {
            // Sumar la cantidad existente a la que se desea agregar.
            int nuevaCantidad = item.getCantidad() + cantidad;
            // Verificar si la cantidad total excede el stock
            if (nuevaCantidad > producto.getCantidad()) {
                return "redirect:/carrito?error=No+haysuficiente+stock+para+agregar";
            }
            item.setCantidad(nuevaCantidad);
            carritoProductoRepository.save(item);
            existe = true;
            break;
        }
    }

    if (!existe) {
        CarritoProductoModel nuevoItem = new CarritoProductoModel(carrito, producto, cantidad);
        carrito.getCarritoProductos().add(nuevoItem);
        carritoProductoRepository.save(nuevoItem);
    }

    return "redirect:/carrito";
}

    @PostMapping("/actualizar/{productoId}")
public String actualizarCantidad(@PathVariable Long productoId,
        @RequestParam int cantidad,
        HttpSession session) {
    ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
    if (cliente == null)
        return "redirect:/login";

    CarritoModel carrito = cliente.getCarrito();
    if (carrito == null)
        return "redirect:/carrito";

    for (CarritoProductoModel item : carrito.getCarritoProductos()) {
        if (item.getProducto().getCodigo().equals(productoId)) {
            if (cantidad > item.getProducto().getCantidad()) {
                return "redirect:/carrito?error=La+cantidad+solicitada+excede+el+stock";
            }
            item.setCantidad(cantidad);
            carritoProductoRepository.save(item);
            break;
        }
    }

    return "redirect:/carrito";
}



    @PostMapping("/eliminarC/{productoId}")
    public String eliminarProductoDelCarrito(@PathVariable Long productoId, HttpSession session) {
        ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
        if (cliente == null)
            return "redirect:/login";

        CarritoModel carrito = cliente.getCarrito();
        if (carrito == null)
            return "redirect:/carrito";

        carrito.getCarritoProductos().removeIf(item -> item.getProducto().getCodigo().equals(productoId));
        carritoRepository.save(carrito);

        return "redirect:/carrito";
    }

    
}
