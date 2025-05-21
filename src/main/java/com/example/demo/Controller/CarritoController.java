package com.example.demo.Controller;

import com.example.demo.Model.CarritoModel;
import com.example.demo.Model.CarritoProductoModel;
import com.example.demo.Model.ClientesModel;
import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.TallaEnum;
import com.example.demo.Model.UsuarioModel;
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
public class CarritoController extends SessionController {

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
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (cliente == null && usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("usuarioLogueado", usuario);

            agregarSesion(session, model);
            
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

        CarritoProductoModel itemEliminar = null;
        for (CarritoProductoModel item : carrito.getCarritoProductos()) {
            if (item.getProducto().getNombre().equals(nombre) && item.getProducto().getTalla().equals(talla)) {
                itemEliminar = item;
                break;
            }
        }

        if (itemEliminar != null) {
            carrito.getCarritoProductos().remove(itemEliminar);
            carritoProductoRepository.delete(itemEliminar); 
            carritoRepository.save(carrito);
        }

        return "redirect:/carrito";
    }

}
