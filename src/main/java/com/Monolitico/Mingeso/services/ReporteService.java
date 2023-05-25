package com.Monolitico.Mingeso.services;

import com.Monolitico.Mingeso.entities.ReporteEntity;
import com.Monolitico.Mingeso.repositories.ReporteRepository;
import lombok.Generated;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class ReporteService {
    @Autowired
    ReporteRepository reporteRepository;

    @Autowired
    AcopioService acopioService;

    private final Logger logg = LoggerFactory.getLogger(ReporteService.class);

    public ArrayList<ReporteEntity> obtenerQuincenas(){
        return (ArrayList<ReporteEntity>) reporteRepository.findAll();
    }

    public ArrayList<ReporteEntity> obtenerQuincenasPorProveedor(String idProveedor){
        return reporteRepository.findByIdProveedor(idProveedor);
    }

    public ReporteEntity obtenerUltimoReporte(String proveedor){
        ArrayList<ReporteEntity> reportes = obtenerQuincenasPorProveedor(proveedor);
        return reportes.get(reportes.size()-1);
    }

    public boolean existeAlgunRep(String idProveedor){
        return reporteRepository.existsAny(idProveedor);
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void guardarData(ReporteEntity data){
        reporteRepository.save(data);
    }

    public void guardarDataDB(String proveedor, String grasa, String solido){

        Double cantLeche = 0.0;
        Integer cantDias = 0;
        Double promLeche = 0.0;
        ReporteEntity newReporte = new ReporteEntity();

        newReporte.setIdProveedor(proveedor);
        newReporte.setGrasa(Double.parseDouble(grasa));
        newReporte.setSolidos(Double.parseDouble(solido));

        //sacamos la info de los acopios
        if(acopioService.exiteAlguno(proveedor)) {

            Date fechaAux = acopioService.conseguirFechaAcopios(proveedor);
            cantLeche = acopioService.totalLecheQuincena(proveedor);
            cantDias = acopioService.cantDias(proveedor);
            promLeche = cantLeche/cantDias;


            if (fechaAux.getDate() > 15) {
                newReporte.setQuincena(2);
            } else {
                newReporte.setQuincena(1);
            }

            newReporte.setMes(fechaAux.getMonth()+1);
            newReporte.setAnio(fechaAux.getYear()+1900);

        }else {

            Date fechaActual = new Date();
            if (fechaActual.getDate() > 15) {
                newReporte.setQuincena(2);
            } else {
                newReporte.setQuincena(1);
            }

            newReporte.setMes(fechaActual.getMonth()+1);
            newReporte.setAnio(fechaActual.getYear()+1900);

        }

        newReporte.setLeche(cantLeche);
        newReporte.setNroDias(cantDias);
        newReporte.setPromedioLeche(promLeche);

        //comparamos con el reporte anterior
        if (existeAlgunRep(proveedor)) {
            ReporteEntity reporteAnterior = obtenerUltimoReporte(proveedor);

            newReporte.setVarGrasa((Double.parseDouble(grasa) - reporteAnterior.getGrasa()) / reporteAnterior.getGrasa());
            newReporte.setVarSolidos((Double.parseDouble(solido) - reporteAnterior.getSolidos()) / reporteAnterior.getSolidos());
            newReporte.setVarCantLeche((cantLeche - reporteAnterior.getLeche())/reporteAnterior.getLeche());
        }else {
            newReporte.setVarGrasa(0.0);
            newReporte.setVarSolidos(0.0);
            newReporte.setVarCantLeche(0.0);
        }

        guardarData(newReporte);
    }
    public void eliminarData(ArrayList<ReporteEntity> datas){
        reporteRepository.deleteAll(datas);
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        //reporteRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }

    }

}
