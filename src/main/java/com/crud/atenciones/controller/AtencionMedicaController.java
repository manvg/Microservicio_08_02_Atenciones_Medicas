package com.crud.atenciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.dto.AtencionMedicaDto;
import com.crud.atenciones.service.AtencionMedica.AtencionMedicaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atenciones")
public class AtencionMedicaController {
    @Autowired
    private AtencionMedicaService atencionMedicaService;

    //Obtener listado completo de atenciones medicas
    @GetMapping
    public List<AtencionMedicaDto> getAllAtencionesMedicas(){
        return atencionMedicaService.getAllAtencionesMedicas();
    }

    //Obtener atención médica por su id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAtencionMedicaById(@PathVariable Integer id){
        var response = atencionMedicaService.getAtencionMedicaById(id);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"La atención médica no existe."));
        }

        return ResponseEntity.ok(response);
    }

    //Obtener todas las atenciones médicas de un paciente por su rut
    @GetMapping("paciente/{rut}")
    public ResponseEntity<Object> getAtencionesMedicasByRut(@PathVariable String rut){
        var response = atencionMedicaService.getAtencionesMedicasByRut(rut);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"El rut ingresado no existe."));
        }

        return ResponseEntity.ok(response);
    }

    //Crear atención médica
    @PostMapping
    public ResponseEntity<Object> createAtencionMedica(@RequestBody @Valid AtencionMedicaDto atencionMedica){
        var response = atencionMedicaService.createAtencionMedica(atencionMedica);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Actualizar atención médica
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAtencionMedica(@PathVariable Integer id, @RequestBody @Valid AtencionMedicaDto atencionMedica){
        var response = atencionMedicaService.updateAtencionMedica(id, atencionMedica);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Eliminar atención médica
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAtencionMedica(@PathVariable Integer id){
        if (atencionMedicaService.getAtencionMedicaById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"La atención médica ingresada no existe."));
        }
        //Eliminar atención medica
        atencionMedicaService.deleteAtencionMedica(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true,"Atención médica eliminada con éxito."));
    }
}
