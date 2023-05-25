package com.Monolitico.Mingeso.controllers;

import com.Monolitico.Mingeso.entities.AcopioEntity;
import com.Monolitico.Mingeso.entities.PagoEntity;
import com.Monolitico.Mingeso.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class PagoController {
    @Autowired
    PagoService pagoService;

    @GetMapping("/pago/info")
    public String listar(Model model){
        ArrayList<PagoEntity> pagos = pagoService.obtenerPagos();
        model.addAttribute("pagos", pagos);
        return "PagosView";
    }

}
