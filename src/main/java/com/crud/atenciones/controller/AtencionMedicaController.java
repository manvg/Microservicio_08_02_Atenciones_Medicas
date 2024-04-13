package com.crud.atenciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.atenciones.model.AtencionMedica;
import com.crud.atenciones.service.AtencionMedicaService;

@RestController
@RequestMapping("/atenciones")
public class AtencionMedicaController {
    @Autowired
    private AtencionMedicaService atencionMedicaService;

    @GetMapping
    public List<AtencionMedica> getAllAtencionesMedicas(){
        return atencionMedicaService.getAllAtencionesMedicas();
    }
}
