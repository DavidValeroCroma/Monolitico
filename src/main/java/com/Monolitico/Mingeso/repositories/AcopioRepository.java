package com.Monolitico.Mingeso.repositories;

import com.Monolitico.Mingeso.entities.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AcopioRepository extends JpaRepository<AcopioEntity, Long> {
    ArrayList<AcopioEntity> findByIdProveedor(String idProveedor);

    AcopioEntity getTopByIdProveedor(String idProveedor);

    @Query("select count(e) > 0 from AcopioEntity e where e.idProveedor = :idProveedor")
    boolean existsAny(@Param("idProveedor") String idProveedor);



}
