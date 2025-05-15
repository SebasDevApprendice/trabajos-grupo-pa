package com.example.demo.Model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ventas")
public class VentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ventaId;

    private Date fechaVenta;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "id") 
    private ClientesModel cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVentaModel> detalles;

    
    public VentaModel() {
    }

    public VentaModel(Long ventaId, Date fechaVenta, Double total, ClientesModel cliente, List<DetalleVentaModel> detalles) {
        this.ventaId = ventaId;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.cliente = cliente;
        this.detalles = detalles;
    }

    
    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ClientesModel getCliente() {
        return cliente;
    }

    public void setCliente(ClientesModel cliente) {
        this.cliente = cliente;
    }

    public List<DetalleVentaModel> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaModel> detalles) {
        this.detalles = detalles;
    }
}
