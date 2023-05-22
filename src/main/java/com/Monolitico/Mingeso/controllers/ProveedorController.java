package com.Monolitico.Mingeso.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @GetMapping
    public String accessToProviderView(){return "ProveedorView";}


}
