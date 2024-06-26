package com.crud.atenciones.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.crud.atenciones.service.Paciente.PacienteService;
import com.crud.atenciones.model.entities.Paciente;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    private static final Logger log = LoggerFactory.getLogger(AtencionMedicaController.class);

    //----------MÉTODOS GET----------//
    //Obtener listado completo de pacientes
    @GetMapping
    public List<Paciente> getAllPacientes(){
        log.info("GET /pacientes -> getAllPacientes");
        log.info("Retornando lista de pacientes");
        return pacienteService.getAllPacientes();
    }

    //Obtener paciente por su rut
    @GetMapping("/{rut}")
    public ResponseEntity<Object> getPacienteByRut(@PathVariable String rut){
        log.info("GET /pacientes -> getPacienteByRut");
        log.info("Obteniendo paciente por su rut " + rut);
        if (!validarRut(rut)) {
            log.error("Rut no válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"Rut no válido."));
        }
        var response = pacienteService.getPacienteByRut(rut);
        if (response.isEmpty()) {
            log.error("El rut ingresado no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"El rut ingresado no existe."));
        }
        log.info("Retornando paciente con rut " + rut);
        return ResponseEntity.ok(response);
    }

    //----------MÉTODOS POST----------//
    //Crear paciente
    @PostMapping
    public ResponseEntity<Object> createPaciente(@RequestBody @Valid Paciente Paciente){
        log.info("POST /pacientes -> createPaciente");
        var response = pacienteService.createPaciente(Paciente);
        if (!response.getStatus()) {
            log.error("No se guardó el paciente");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info(response.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //----------MÉTODOS PUT----------//
    //Actualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePaciente(@PathVariable Integer id, @RequestBody @Valid Paciente Paciente){
        log.info("POST /pacientes -> updatePaciente id " + id);
        var response = pacienteService.updatePaciente(id, Paciente);
        if (!response.getStatus()) {
            log.error(response.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //---------MÉTODOS DELETE---------//
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaciente(@PathVariable Integer id){
        log.info("DELETE /Pacientes/"+id);
        log.info("Eliminando Paciente con id " + id);
  
        var response = pacienteService.deletePaciente(id);
        if (!response.getStatus()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info("Paciente eliminado con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //----------OTROS MÉTODOS----------//
    private boolean validarRut(String rut) {
        return rut != null && rut.matches("\\d{7,8}-[\\dkK]");
    }
}
