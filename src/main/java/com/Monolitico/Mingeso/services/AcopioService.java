package com.Monolitico.Mingeso.services;

import com.Monolitico.Mingeso.entities.AcopioEntity;
import com.Monolitico.Mingeso.repositories.AcopioRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.Generated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.text.ParseException;

@Service
public class AcopioService {
    @Autowired
    AcopioRepository acopioRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioService.class);

    public ArrayList<AcopioEntity> obtenerAcopios() {
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }

    public ArrayList<AcopioEntity> obtenerAcopioPorProveedor(String idProveedor){
        return acopioRepository.findByIdProveedor(idProveedor);
    }

    public boolean exiteAlguno(String idProvedor){
        return acopioRepository.existsAny(idProvedor);
    }

    public Double totalLecheQuincena(String idProveedor){

        ArrayList<AcopioEntity> acopioQuincena = obtenerAcopioPorProveedor(idProveedor);
        double acum = 0;

        for (AcopioEntity acopio:acopioQuincena){
            acum = acum + acopio.getLeche();
        }

        return acum;
    }

    public Date conseguirFechaAcopios(String proveedor){
        AcopioEntity acopioAux = acopioRepository.getTopByIdProveedor(proveedor);
        return acopioAux.getFecha();
    }

    public int cantDias(String idProveedor){
        ArrayList<AcopioEntity> acopios = obtenerAcopioPorProveedor(idProveedor);
        int acum = 0;
        Date fechaAnt = new Date();
        for (AcopioEntity acopio: acopios){
            if (acopio.getFecha() != fechaAnt){
                acum = acum + 1;
            }
            fechaAnt = acopio.getFecha();
        }
        return acum;
    }

    public Double bonificacionCons(String idProveedor){

        int diasSeguidos = 0;
        int diasTardeSeguidos = 0;
        int diasMañanaSeguidos = 0;
        Character turnoPrevio = 'O';
        Character turnoDiaPrevio = 'O';
        int diaPrevio = 200;

        if(acopioRepository.existsAny(idProveedor)) {
            ArrayList<AcopioEntity> acopios = obtenerAcopioPorProveedor(idProveedor);
            //calculamos los dias y/o turnos seguidos
            for (AcopioEntity acopio : acopios) {

                if (acopio.getFecha().getDay() == diaPrevio + 1) {
                    if ((turnoPrevio == 'M' || turnoDiaPrevio == 'M') && acopio.getTurno() == 'M') {
                        diasMañanaSeguidos = diasMañanaSeguidos + 1;
                    } else if ((turnoPrevio == 'T' || turnoDiaPrevio == 'T') && acopio.getTurno() == 'T') {
                        diasTardeSeguidos = diasTardeSeguidos + 1;
                    } else {
                        diasMañanaSeguidos = 0;
                        diasTardeSeguidos = 0;
                    }

                } else if (acopio.getFecha().getDay() == diaPrevio) {
                    if (turnoPrevio == 'M' && acopio.getTurno() == 'T') {
                        diasSeguidos = diasSeguidos + 1;
                    }
                } else {
                    diasSeguidos = 0;
                    diasMañanaSeguidos = 0;
                    diasTardeSeguidos = 0;
                }

                turnoDiaPrevio = turnoPrevio;
                turnoPrevio = acopio.getTurno();
                diaPrevio = acopio.getFecha().getDay();

            }
        }
        //Vemos cual es la bonificación que le corresponde

        if(diasSeguidos >= 10){
            return 0.2;

        }else if(diasMañanaSeguidos >= 10){
            return 0.12;

        }else if(diasTardeSeguidos >= 10){
            return 0.08;
        }
        return  0.0;

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
    public void guardarData(AcopioEntity data){
        acopioRepository.save(data);
    }

    public void guardarDataDB(String fecha, String turno, String proveedor, String klsLeche){
        AcopioEntity newAcopio = new AcopioEntity();
        System.out.println(fecha);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaAux = null;
        try{
            fechaAux = formato.parse(fecha);

            newAcopio.setFecha(fechaAux);
            newAcopio.setIdProveedor(proveedor);
            newAcopio.setTurno(turno.charAt(0));
            newAcopio.setLeche(Double.parseDouble(klsLeche));
            guardarData(newAcopio);
        }catch(ParseException e){
            System.out.println("Error al parsear la fecha.");
            e.printStackTrace();
        }
    }
    public void eliminarData(ArrayList<AcopioEntity> datas){
        acopioRepository.deleteAll(datas);
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        acopioRepository.deleteAll();
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
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
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



