package com.Monolitico.Mingeso.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReporteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false, unique = true)
    private Long id;
    private String idProveedor;
    private Double leche;
    private Integer quincena;
    private Integer mes;
    private Integer anio;
    private Double solidos;
    private Double grasa;
    private Double varSolidos;
    private Double varGrasa;
    private Double varCantLeche;
    private Double promedioLeche;
    private Integer nroDias;
}
