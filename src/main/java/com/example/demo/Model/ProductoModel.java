package com.example.demo.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "productos")

public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "talla", nullable = false)
    private String talla;
    @Column(name = "categoria", nullable = false)
    private String categoria;

    public ProductoModel() {
    }

    public ProductoModel(long codigo, String nombre, int cantidad, double precio, String talla, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.talla = talla;
        this.categoria = categoria;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "productoModel{" + "codigo=" + codigo + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio + ", talla=" + talla + ", categoria=" + categoria + '}';
    }
}
