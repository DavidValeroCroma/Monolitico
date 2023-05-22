package com.Monolitico.Mingeso.repositories;

import com.Monolitico.Mingeso.entities.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AcopioRepository extends JpaRepository<AcopioEntity, Long> {
    ArrayList<AcopioEntity> findByIdProveedor(String idProveedor);

}
