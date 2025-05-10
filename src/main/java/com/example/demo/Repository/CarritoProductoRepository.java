package com.example.demo.Repository;

import com.example.demo.Model.CarritoProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoProductoRepository extends JpaRepository<CarritoProductoModel, Long> {
}

