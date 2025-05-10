package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito")
public class CarritoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCarrito;
    @Column(name = "nombre", nullable = false)
    private float costoCarrito;
    @OneToOne(mappedBy = "carrito")
    private UsuarioModel usuario;
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoProductoModel> carritoProductos = new ArrayList<>();

    public CarritoModel() {
    }

    public CarritoModel(long idCarrito, float costoCarrito, UsuarioModel usuario,
            List<CarritoProductoModel> carritoProductos) {
        this.idCarrito = idCarrito;
        this.costoCarrito = costoCarrito;
        this.usuario = usuario;
        this.carritoProductos = carritoProductos;
    }

    public long getIdCarrito() {
        return idCarrito;
    }
    public void setIdCarrito(long idCarrito) {
        this.idCarrito = idCarrito;
    }
    public float getCostoCarrito() {
        return costoCarrito;
    }
    public void setCostoCarrito(float costoCarrito) {
        this.costoCarrito = costoCarrito;
    }
    public UsuarioModel getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
    public List<CarritoProductoModel> getCarritoProductos() {
        return carritoProductos;
    }
    public void setCarritoProductos(List<CarritoProductoModel> carritoProductos) {
        this.carritoProductos = carritoProductos;
    }

}
