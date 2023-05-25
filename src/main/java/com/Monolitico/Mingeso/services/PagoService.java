package com.Monolitico.Mingeso.services;

import com.Monolitico.Mingeso.entities.PagoEntity;
import com.Monolitico.Mingeso.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    ReporteService reporteService;



    public ArrayList<PagoEntity> obtenerPagos(){
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    public void generarPago(String proveedor, Long idReporte, Integer quincena){


    }

}
