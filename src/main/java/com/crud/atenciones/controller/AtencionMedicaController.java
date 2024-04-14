package com.crud.atenciones.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.dto.AtencionMedicaDto;
import com.crud.atenciones.service.AtencionMedica.AtencionMedicaService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/atenciones")
public class AtencionMedicaController {
    private static final Logger log = LoggerFactory.getLogger(AtencionMedicaController.class);

    @Autowired
    private AtencionMedicaService atencionMedicaService;

    //Obtener listado completo de atenciones medicas
    @GetMapping
    public List<AtencionMedicaDto> getAllAtencionesMedicas(){
        log.info("GET /atenciones -> getAllAtencionesMedicas");
        log.info("Retornando lista de atenciones medicas");
        return atencionMedicaService.getAllAtencionesMedicas();
    }

    //Obtener atención médica por su id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAtencionMedicaById(@PathVariable Integer id){
        log.info("GET /atenciones/" + id + " -> getAtencionMedicaById");
        log.info("Obteniendo atencion medica por id " + id);
        var response = atencionMedicaService.getAtencionMedicaById(id);
        if (response.isEmpty()) {
            log.error("No se encontro atencion medica con id " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"La atención médica no existe."));
        }
        log.info("Atencion medica encontrada con exito. Id: " + id);
        return ResponseEntity.ok(response);
    }

    //Obtener todas las atenciones médicas de un paciente por su rut
    @GetMapping("paciente/{rut}")
    public ResponseEntity<Object> getAtencionesMedicasByRut(@PathVariable String rut){
        log.info("GET /atenciones/paciente/" + rut + " -> getAtencionesMedicasByRut");
        log.info("Obteniendo atenciones medicas del paciente con rut " + rut);
        var response = atencionMedicaService.getAtencionesMedicasByRut(rut);
        if (response.isEmpty()) {
            log.error("El paciente con rut " + rut + " no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"El rut ingresado no existe."));
        }
        log.info("Se encontraron " + response.size() + " atenciones medicas para el paciente con rut " + rut);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-rango-fecha")
    public ResponseEntity<Object> getAtencionMedicaByRangoFecha(@RequestParam(value = "fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio, @RequestParam(value = "fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        log.info("GET /atenciones/by-rango-fecha -> getAtencionMedicaByRangoFecha");
        log.info("Obteniendo atenciones medicas con rango fecha inicio: " + fechaInicio + " y fecha fin " + fechaFin);
        if (fechaInicio.isAfter(fechaFin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false, "La fecha de inicio no puede ser mayor que fecha de fin."));
        }

        List<AtencionMedicaDto> response = atencionMedicaService.getAtencionesMedicasByRangoFecha(fechaInicio, fechaFin);

        if (response.isEmpty()) {
            log.error("No se encontraron atenciones medicas en el rango de fechas : " + fechaInicio + " y fecha fin " + fechaFin);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false, "No se encontraron atenciones médicas para el rango de fechas especificado."));
        }
        log.info("Se encontraron " + response.size() + " atenciones medicas en el rango de fechas : " + fechaInicio + " y fecha fin " + fechaFin);
        return ResponseEntity.ok(response);
    }

    //Crear atención médica
    @PostMapping
    public ResponseEntity<Object> createAtencionMedica(@RequestBody @Valid AtencionMedicaDto atencionMedica){
        log.info("POST /atenciones/createAtencionMedica -> createAtencionMedica");
        log.info("Creando atencion medica...");
        var response = atencionMedicaService.createAtencionMedica(atencionMedica);
        if (!response.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info(response.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Actualizar atención médica
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAtencionMedica(@PathVariable Integer id, @RequestBody @Valid AtencionMedicaDto atencionMedica){
        log.info("PUT /atenciones/"+id + " -> updateAtencionMedica");
        log.info("Actualizando atencion medica");
        var response = atencionMedicaService.updateAtencionMedica(id, atencionMedica);
        if (!response.getStatus()) {
            log.error(response.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info("Atención médica actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Eliminar atención médica
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAtencionMedica(@PathVariable Integer id){
        log.info("DELETE /atenciones/" + id + " -> deleteAtencionMedica");
        if (atencionMedicaService.getAtencionMedicaById(id).isEmpty()) {
            log.error("La atención médica con id " + id + " no existe");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(false,"La atención médica ingresada no existe."));
        }
        //Eliminar atención medica
        atencionMedicaService.deleteAtencionMedica(id);
        log.info("Atención médica eliminada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true,"Atención médica eliminada con éxito."));
    }
}
