package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.DetalleVentaModel;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaModel, Long> {}
