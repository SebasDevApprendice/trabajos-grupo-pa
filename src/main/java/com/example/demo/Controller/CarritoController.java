package com.example.demo.Controller;

import com.example.demo.Model.CarritoModel;
import com.example.demo.Model.CarritoProductoModel;
import com.example.demo.Model.ClientesModel;
import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.TallaEnum;
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

    @PostMapping("/agregarAlCarrito")
    public String agregarAlCarrito(@RequestParam String nombre,
            @RequestParam TallaEnum talla,
            @RequestParam int cantidad,
            HttpSession session) {
        ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            return "redirect:/login";
        }

        // Buscar el producto exacto por nombre y talla
        ProductoModel producto = productoRepository.findByNombreAndTalla(nombre, talla)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

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

        // ✅ Verificamos si ya hay ese producto con esa talla
        boolean existe = false;
        for (CarritoProductoModel item : carrito.getCarritoProductos()) {
            if (item.getProducto().getNombre().equals(nombre) &&
                    item.getProducto().getTalla() == talla) {

                int nuevaCantidad = item.getCantidad() + cantidad;

                if (nuevaCantidad > producto.getCantidad()) {
                    return "redirect:/carrito?error=No+haysuficiente+stock+para+agregar";
                }

                item.setCantidad(nuevaCantidad);
                carritoProductoRepository.save(item);
                existe = true;
                break;
            }
        }

        // Si no existía esa combinación nombre+talla, se agrega como nuevo ítem
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

    @PostMapping("/eliminarC")
    public String eliminarProductoDelCarrito(@RequestParam String nombre,
            @RequestParam TallaEnum talla,
            HttpSession session) {
        ClientesModel cliente = (ClientesModel) session.getAttribute("clienteLogueado");
        if (cliente == null)
            return "redirect:/login";

        CarritoModel carrito = cliente.getCarrito();
        if (carrito == null)
            return "redirect:/carrito";

        // Buscar el producto en el carrito
        CarritoProductoModel itemEliminar = null;
        for (CarritoProductoModel item : carrito.getCarritoProductos()) {
            if (item.getProducto().getNombre().equals(nombre) && item.getProducto().getTalla().equals(talla)) {
                itemEliminar = item;
                break;
            }
        }

        if (itemEliminar != null) {
            carrito.getCarritoProductos().remove(itemEliminar); // quitar de la lista
            carritoProductoRepository.delete(itemEliminar); // borrar de BD
            carritoRepository.save(carrito); // guardar carrito actualizado (opcional)
        }

        return "redirect:/carrito";
    }

}
