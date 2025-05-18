package com.example.demo.DTO;

import java.util.List;

import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.TallaEnum;

public class ProductoDto {
    private ProductoModel producto;
    private List<TallaEnum> tallasDisponibles;

    public ProductoDto(ProductoModel producto, List<TallaEnum> tallasDisponibles) {
        this.producto = producto;
        this.tallasDisponibles = tallasDisponibles;
    }

    public ProductoModel getProducto() {
        return producto;
    }

    public List<TallaEnum> getTallasDisponibles() {
        return tallasDisponibles;
    }
}
