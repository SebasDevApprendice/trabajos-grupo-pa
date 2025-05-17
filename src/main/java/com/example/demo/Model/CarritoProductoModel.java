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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_codigo", nullable = false)
    private ProductoModel producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    public CarritoProductoModel() {
    }

    

    public CarritoProductoModel(CarritoModel carrito, ProductoModel producto, int cantidad) {
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

    public ProductoModel getProducto() {
        return producto;
    }

    public void setProducto(ProductoModel producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
