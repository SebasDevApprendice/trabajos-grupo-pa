package com.example.demo.Repository;

import com.example.demo.Model.CarritoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoModel, Long> {
    
}
