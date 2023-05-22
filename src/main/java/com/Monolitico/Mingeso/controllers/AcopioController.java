package com.Monolitico.Mingeso.controllers;

import com.Monolitico.Mingeso.entities.AcopioEntity;
import com.Monolitico.Mingeso.services.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class AcopioController {
    @Autowired
    private AcopioService acopioService;

    @GetMapping("/acopio/fileUpload")
    public String main(){return "AcopioView";}

    @PostMapping("/acopio/fileUpload")
    public String upload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        acopioService.guardar(file);
        redirectAttributes.addFlashAttribute(file);
        acopioService.leerCsv("Acopio.csv");
        return "redirect:/acopio/fileInformation";
    }

    @GetMapping("/acopio/fileInformation")
    public String listar(Model model){
        ArrayList<AcopioEntity> acopios = acopioService.obtenerAcopio();
        model.addAttribute("acopios", acopios);
        return "AcopioInfoView";
    }
}
