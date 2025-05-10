package com.example.demo.Repository;

import com.example.demo.Model.usuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<usuarioModel, Long> {
}

