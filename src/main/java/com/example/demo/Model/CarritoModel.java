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
    @Column(name = "costoCarrito", nullable = false)
    private float costoCarrito;
    @OneToOne(mappedBy = "carrito")
    private ClientesModel cliente;
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CarritoProductoModel> carritoProductos = new ArrayList<>();

    public CarritoModel() {
    }

    public CarritoModel(long id, float costoCarrito, ClientesModel cliente, List<CarritoProductoModel> carritoProductos) {
        this.id = id;
        this.costoCarrito = costoCarrito;
        this.cliente = cliente;
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
    public ClientesModel getCliente() {
        return cliente;
    }
    public void setCliente(ClientesModel cliente) {
        this.cliente = cliente;
    }
    public List<CarritoProductoModel> getCarritoProductos() {
        return carritoProductos;
    }
    public void setCarritoProductos(List<CarritoProductoModel> carritoProductos) {
        this.carritoProductos = carritoProductos;
    }

}
