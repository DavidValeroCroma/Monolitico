package com.Monolitico.Mingeso.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String main(){
        return "HomeView";
    }

    @GetMapping("error")
    public String error(){ return "error";}
}
