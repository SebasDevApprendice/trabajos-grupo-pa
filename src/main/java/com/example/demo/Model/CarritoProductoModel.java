package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "carritoProducto")
public class CarritoProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    private CarritoModel carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_codigo", nullable = false)
    private productoModel producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    public CarritoProductoModel() {
    }

    public CarritoProductoModel(CarritoModel carrito, productoModel producto, int cantidad) {
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarritoModel getCarrito() {
        return carrito;
    }

    public void setCarrito(CarritoModel carrito) {
        this.carrito = carrito;
    }

    public productoModel getProducto() {
        return producto;
    }

    public void setProducto(productoModel producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
