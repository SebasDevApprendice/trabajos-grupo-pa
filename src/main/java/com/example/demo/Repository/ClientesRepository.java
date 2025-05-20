package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.ClientesModel;

@Repository
public interface ClientesRepository extends JpaRepository<ClientesModel, Long> {
    ClientesModel findByEmail(String email);
    boolean existsByEmail(String email);
}