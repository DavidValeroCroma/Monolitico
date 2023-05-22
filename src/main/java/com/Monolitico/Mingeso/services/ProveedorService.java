package com.Monolitico.Mingeso.services;

import com.Monolitico.Mingeso.entities.ProveedorEntity;
import com.Monolitico.Mingeso.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity>  obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }
    public ProveedorEntity obtenerProveedorPorId(String idProveedor){
        return proveedorRepository.getReferenceById(idProveedor);
    }

    public void guardarProveedor(ProveedorEntity data){proveedorRepository.save(data);}
}
