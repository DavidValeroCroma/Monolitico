package com.Monolitico.Mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProveedorEntity {
    @Id
    @Column(nullable = false, length = 5, unique = true)
    private String id;
    private String nombre;
    private Character categoria;
    private Boolean retencion;
}
