package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito")
public class CarritoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre", nullable = false)
    private float costoCarrito;
    @OneToOne(mappedBy = "carrito")
    private UsuarioModel usuario;
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoProductoModel> carritoProductos = new ArrayList<>();

    public CarritoModel() {
    }

    public CarritoModel(long id, float costoCarrito, UsuarioModel usuario, List<CarritoProductoModel> carritoProductos) {
        this.id = id;
        this.costoCarrito = costoCarrito;
        this.usuario = usuario;
        this.carritoProductos = carritoProductos;
    }

    public long getid() {
        return id;
    }
    public void setid(long id) {
        this.id = id;
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
