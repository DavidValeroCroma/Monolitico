package com.Monolitico.Mingeso.repositories;


import com.Monolitico.Mingeso.entities.ReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteEntity, Long> {
    ArrayList<ReporteEntity> findByIdProveedor(String idProveedor);

    @Query("select count(e) > 0 from ReporteEntity e where e.idProveedor = :idProveedor")
    boolean existsAny(@Param("idProveedor") String idProveedor);
}
