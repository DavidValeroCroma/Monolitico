package com.Monolitico.Mingeso.controllers;

import com.Monolitico.Mingeso.entities.ReporteEntity;
import com.Monolitico.Mingeso.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class ReporteController {
    @Autowired
    ReporteService reporteService;

    @GetMapping("/reporte/fileUpload")
    public String main(){return "RegistroView";}

    @PostMapping("/reporte/fileUpload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        reporteService.guardar(file);
        redirectAttributes.addFlashAttribute(file);
        reporteService.leerCsv("Quincena.csv");
        return "redirect:/reporte/fileInformation";
    }

    @GetMapping("/reporte/fileInformation")
    public String listar(Model model){
        ArrayList<ReporteEntity> reporte = reporteService.obtenerQuincenas();
        model.addAttribute("reporte", reporte);
        return "RegistroInfoView";
    }


}
