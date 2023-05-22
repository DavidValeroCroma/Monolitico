package com.Monolitico.Mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "acopio")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AcopioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    private String idProveedor;
    private Date fecha;
    private Character turno;
    private Double leche;
}
