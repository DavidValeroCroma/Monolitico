package com.Monolitico.Mingeso.repositories;


import com.Monolitico.Mingeso.entities.ReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteEntity, Long> {
    ArrayList<ReporteEntity> findByIdProveedor(String idProveedor);
    ReporteEntity findByIdProveedorAndQuincenaAndMesAndAnio(String idProveedor, Integer quincena, Integer mes, Integer anio);
}
