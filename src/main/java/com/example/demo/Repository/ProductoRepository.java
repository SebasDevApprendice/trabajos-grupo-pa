package com.example.demo.Repository;

import com.example.demo.Model.ProductoModel;
import com.example.demo.Model.TallaEnum;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {
    Optional<ProductoModel> findByNombreAndTalla(String nombre, TallaEnum talla);
}
