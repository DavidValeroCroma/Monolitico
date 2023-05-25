package com.Monolitico.Mingeso.services;

import com.Monolitico.Mingeso.entities.PagoEntity;
import com.Monolitico.Mingeso.entities.ProveedorEntity;
import com.Monolitico.Mingeso.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    ProveedorService proveedorService;
    @Autowired
    AcopioService acopioService;


    public ArrayList<PagoEntity> obtenerPagos(){
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    public void generarPago(Long idRep, String idProveedor, Double leche, Integer quincena, Integer mes, Integer anio, Double solidos, Double grasa, Double varSolidos, Double varGrasa, Double varCantLeche, Double promedioLeche, Double porGrasa, Double porSolidos){
        ProveedorEntity proveedorAux = proveedorService.obtenerProveedorPorId(idProveedor);
        PagoEntity nuevoPago = new PagoEntity();
        nuevoPago.setIdReporte(idRep);
        nuevoPago.setIdProveedor(idProveedor);
        nuevoPago.setQuincena(quincena);
        nuevoPago.setMes(mes);
        nuevoPago.setAnio(anio);

        //calculamos el pago total de leche
        double pagoLeche = 0;
        if(proveedorAux.getCategoria().equals('A')){
            pagoLeche = leche * 700;
            
        } else if (proveedorAux.getCategoria().equals('B')) {
            pagoLeche = leche * 550;
            
        } else if (proveedorAux.getCategoria().equals('C')) {
            pagoLeche = leche * 400;
            
        } else if (proveedorAux.getCategoria().equals('D')) {
            pagoLeche = leche * 250;

        }

        nuevoPago.setPagoLeche(pagoLeche);

        //calculamos el pago por grasa

        double pagoGrasa = 0;

        if (porGrasa<=20.0){
            pagoGrasa = leche * 30;
        } else if (porGrasa<=45.0  && porGrasa>20.0) {
            pagoGrasa = leche * 80;
        } else if (porGrasa<100.0 && porGrasa>45.0) {
            pagoGrasa = leche * 120;
        }

        nuevoPago.setPagoGrasa(pagoGrasa);
        //calculamos el pago por solidos

        double pagoSolidos = 0.0;

        if (porSolidos < 7.0){
            pagoSolidos = leche * -130;
        } else if (porSolidos < 18.0 && porSolidos > 7.0) {
            pagoSolidos = leche * -90;
        } else if (porSolidos < 35.0 && porSolidos > 18.0){
            pagoSolidos = leche * 95;
        } else if (porSolidos < 100.0 && porSolidos > 35.0){
            pagoSolidos = leche * 150;
        }

        nuevoPago.setPagoSolidos(pagoSolidos);

        //Sacamos el pago por acopio de leche

        double bonificacion = pagoLeche * acopioService.bonificacionCons(idProveedor);

        double pagoAcopioLeche = pagoLeche + pagoGrasa + pagoSolidos + bonificacion;

        //sacamos los descuentos

        double descVarLeche = 0;
        double descVarGrasa = 0;
        double descVarSolido = 0;

        if (varCantLeche <= 25.0 && varCantLeche > 8.0) {
            descVarLeche = pagoAcopioLeche * 0.07;
        } else if (varCantLeche < 40.0 && varCantLeche > 25.0) {
            descVarLeche = pagoAcopioLeche - pagoAcopioLeche * 0.15;
        } else if (varCantLeche < 100.0 && varCantLeche > 40.0) {
            descVarLeche = pagoAcopioLeche - pagoAcopioLeche * 0.3;
        }

        if(varGrasa <= 25 && varGrasa >= 15){
            descVarGrasa = pagoAcopioLeche * 0.12;

        } else if (varGrasa <= 45 && varGrasa > 25) {
            descVarGrasa = pagoAcopioLeche * 0.20;

        } else if (varGrasa <= 100 && varGrasa > 45) {
            descVarGrasa = pagoAcopioLeche * 0.30;
        }

        if (varSolidos <= 12 && varSolidos >= 6){
            descVarSolido = pagoAcopioLeche * 0.18;

        } else if (varSolidos <= 35 && varSolidos > 12) {
            descVarSolido = pagoAcopioLeche * 0.27;

        } else if (varSolidos <= 100 && varSolidos > 35) {
            descVarSolido = pagoAcopioLeche * 0.45;
        }

        double descuentosTotales = descVarSolido + descVarGrasa + descVarLeche;

        nuevoPago.setDescVarGrasa(descVarGrasa);
        nuevoPago.setDescVarSol(descVarSolido);
        nuevoPago.setDescVarLeche(descVarLeche);

        double pagoTotal = pagoAcopioLeche - descuentosTotales;

        nuevoPago.setPagoTotal(pagoTotal);

        double pagoFinal = pagoTotal;

        if (proveedorAux.getRetencion() == true && pagoTotal > 950000){
            pagoFinal = pagoTotal - 0.13*pagoTotal;
        }

        nuevoPago.setPagoFinal(pagoFinal);

        pagoRepository.save(nuevoPago);

    }

}
