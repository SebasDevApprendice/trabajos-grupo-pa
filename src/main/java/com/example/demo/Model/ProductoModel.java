package com.example.demo.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "productos")
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "talla", nullable = false)
    private TallaEnum talla;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaEnum categoria;
    
    @Column(name = "imagen_url")
    private String imagenUrl;

    public ProductoModel() {
    }

    public ProductoModel(Long codigo, String nombre, Integer cantidad, Double precio, TallaEnum talla, CategoriaEnum categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.talla = talla;
        this.categoria = categoria;
    }

    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public CategoriaEnum getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaEnum categoria) {
        this.categoria = categoria;
    }
    
    public TallaEnum getTalla() {
        return talla;
    }
    public void setTalla(TallaEnum talla) {
        this.talla = talla;
    }
    
}

