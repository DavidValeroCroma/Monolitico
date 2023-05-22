package com.Monolitico.Mingeso.services;

import com.Monolitico.Mingeso.entities.ReporteEntity;
import com.Monolitico.Mingeso.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class ReporteService {
    @Autowired
    ReporteRepository reporteRepository;
    @Autowired
    AcopioService acopioService;

    public ArrayList<ReporteEntity> obtenerQuincenas(){
        return (ArrayList<ReporteEntity>) reporteRepository.findAll();
    }

    public ArrayList<ReporteEntity> obtenerQuincenasPorProveedor(String idProveedor){
        return (ArrayList<ReporteEntity>) reporteRepository.findByIdProveedor(idProveedor);
    }


}
