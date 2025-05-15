package com.example.demo.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_ventas")
public class DetalleVentaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CompraId;

    @ManyToOne
    @JoinColumn(name = "VentaId")
    private VentaModel venta;

    @ManyToOne
    @JoinColumn(name = "codigo")
    private ProductoModel producto;

    private Integer cantidad;
    private Double subtotal;

    
    public DetalleVentaModel(Long CompraId, VentaModel venta, ProductoModel producto, Integer cantidad, Double subtotal) {
        this.CompraId = CompraId;
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public DetalleVentaModel() {
    }

    public Long getId() {
        return CompraId;
    }
    public void setId(Long CompraId) {
        this.CompraId = CompraId;
    }
    public VentaModel getVenta() {
        return venta;
    }
    public void setVenta(VentaModel venta) {
        this.venta = venta;
    }
    public ProductoModel getProducto() {
        return producto;
    }
    public void setProducto(ProductoModel producto) {
        this.producto = producto;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

}
