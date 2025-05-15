package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.VentaModel;

@Repository
public interface VentaRepository extends JpaRepository<VentaModel, Long> {}
