package com.crud.atenciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.atenciones.model.AtencionMedica;
import com.crud.atenciones.model.ResponseModel;
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAtencionMedicaById(@PathVariable Integer id){
        var response = atencionMedicaService.getAtencionMedicaById(id);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"El paciente ingresado no existe."));
        }

        return ResponseEntity.ok(response);
    }
}
