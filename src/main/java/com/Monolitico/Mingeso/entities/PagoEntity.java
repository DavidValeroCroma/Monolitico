package com.Monolitico.Mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false, unique = true)
    private Long id;
    private String idProveedor;
    private Long idReporte;
    private Integer quincena;
    private Integer mes;
    private Integer anio;
    private Double pagoTotal;
    private Double pagoLeche;
    private Double pagoGrasa;
    private Double pagoSolidos;
    private Double bonoFrec;
    private Double descVarLeche;
    private Double descVarGrasa;
    private Double descVarSol;
    private Double montoRet;
    private Double pagoFinal;

}