package com.Monolitico.Mingeso.repositories;

import com.Monolitico.Mingeso.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity,String> {
}
