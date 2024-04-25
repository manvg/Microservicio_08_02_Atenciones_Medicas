package com.crud.atenciones.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import com.crud.atenciones.advice.BusinessException;
import com.crud.atenciones.advice.GeneralNotFoundException;
import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.service.AtencionMedica.AtencionMedicaService;
import com.crud.atenciones.service.Paciente.PacienteService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/atenciones")
public class AtencionMedicaController {
    private static final Logger log = LoggerFactory.getLogger(AtencionMedicaController.class);

    @Autowired
    private AtencionMedicaService atencionMedicaService;

    @Autowired 
    private PacienteService pacienteService;

    //----------MÉTODOS GET----------//
    //Obtener listado completo de atenciones medicas
    @GetMapping
    public CollectionModel<EntityModel<AtencionMedica>> getAllAtencionesMedicas() {
        log.info("GET /atenciones -> getAllAtencionesMedicas");
        log.info("Retornando lista de atenciones medicas");
        var atencionesMedicas = atencionMedicaService.getAllAtencionesMedicas();
        List<EntityModel<AtencionMedica>> atencionesResources = atencionesMedicas.stream()
            .map( atencion -> EntityModel.of(atencion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(atencion.getIdAtencionMedica())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencionesMedicas());
        CollectionModel<EntityModel<AtencionMedica>> resources = CollectionModel.of(atencionesResources, linkTo.withRel("atenciones-medicas"));

        return resources;
    }

    //Obtener atención médica por su id
    @GetMapping("/{id}")
    public EntityModel<AtencionMedica> getAtencionMedicaById(@PathVariable Integer id){
        log.info("GET /atenciones/" + id + " -> getAtencionMedicaById");
        log.info("Obteniendo atencion medica por id " + id);
        var atencionMedica = atencionMedicaService.getAtencionMedicaById(id);

        if (!atencionMedica.isEmpty()) {
            log.info("Atencion medica encontrada con exito. Id: " + id);
            return EntityModel.of(atencionMedica.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencionesMedicas()).withRel("all-atenciones-medicas"));
        } else {
            log.error("No se encontro atencion medica con id " + id);
            throw new GeneralNotFoundException("Atención Médica Id: " + String.valueOf(id));
        }
    }

    //Obtener todas las atenciones médicas de un paciente por su rut
    @GetMapping("paciente/{rut}")
    public CollectionModel<EntityModel<AtencionMedica>> getAtencionesMedicasByRut(@PathVariable String rut){
        log.info("GET /atenciones/paciente/" + rut + " -> getAtencionesMedicasByRut");

        log.info("Validando existencia de rut " + rut);
        var existeRut = pacienteService.getPacienteByRut(rut);
        if (existeRut.isEmpty()) {
            log.error("No existe un paciente con rut " + rut + " no existe");
            throw new BusinessException("RUT_NO_EXISTE", "No existe un paciente con rut " + rut + " no existe");
        }

        log.info("Obteniendo atenciones medicas del paciente con rut " + rut);
        var atencionesMedicas = atencionMedicaService.getAtencionesMedicasByRut(rut);
        List<EntityModel<AtencionMedica>> atencionesResources = atencionesMedicas.stream()
            .map( atencion -> EntityModel.of(atencion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(atencion.getIdAtencionMedica())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionesMedicasByRut(rut));
        CollectionModel<EntityModel<AtencionMedica>> resources = CollectionModel.of(atencionesResources, linkTo.withRel("atenciones-medicas-by-rut"));
        log.info("Se encontraron " + atencionesMedicas.size() + " atenciones medicas para el paciente con rut " + rut);
        return resources;
    }


    @GetMapping("/by-rango-fecha")
    public CollectionModel<EntityModel<AtencionMedica>> getAtencionMedicaByRangoFecha(@RequestParam(value = "fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio, @RequestParam(value = "fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        log.info("GET /atenciones/by-rango-fecha -> getAtencionMedicaByRangoFecha");
        log.info("Obteniendo atenciones medicas con rango fecha inicio: " + fechaInicio + " y fecha fin " + fechaFin);
        if (fechaInicio.isAfter(fechaFin)) {
            throw new BusinessException("RANGO_FECHA_INVALIDO", "La fecha de inicio no puede ser mayor que fecha de fin.");
        }

        List<AtencionMedica> atencionesMedicas = atencionMedicaService.getAtencionesMedicasByRangoFecha(fechaInicio, fechaFin);
        List<EntityModel<AtencionMedica>> atencionesResources = atencionesMedicas.stream()
            .map( atencion -> EntityModel.of(atencion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(atencion.getIdAtencionMedica())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaByRangoFecha(fechaInicio, fechaFin));
        CollectionModel<EntityModel<AtencionMedica>> resources = CollectionModel.of(atencionesResources, linkTo.withRel("atenciones-medicas-by-rango-fecha"));

        log.info("Se encontraron " + atencionesMedicas.size() + " atenciones medicas en el rango de fechas : " + fechaInicio + " y fecha fin " + fechaFin);
        return resources;
    }

    //----------MÉTODOS POST----------//
    //Crear atención médica
    @PostMapping
    public ResponseEntity<Object> createAtencionMedica(@RequestBody @Valid AtencionMedica atencionMedica){
        log.info("POST /atenciones/createAtencionMedica -> createAtencionMedica");
        log.info("Creando atencion medica...");
        var response = atencionMedicaService.createAtencionMedica(atencionMedica);
        if (!response.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info(response.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //----------MÉTODOS PUT----------//
    //Actualizar atención médica
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAtencionMedica(@PathVariable Integer id, @RequestBody @Valid AtencionMedica atencionMedica){
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

    //----------MÉTODOS DELETE----------//
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
