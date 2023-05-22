package com.Monolitico.Mingeso.repositories;


import com.Monolitico.Mingeso.entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
    PagoEntity findByIdProveedor(String idProveedor);
}
