package com.crud.atenciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.dto.PacienteDto;
import com.crud.atenciones.model.dto.PacienteDto;
import com.crud.atenciones.service.Paciente.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
    //Obtener listado completo de atenciones medicas
    @GetMapping
    public List<PacienteDto> getAllAtencionesMedicas(){
        return pacienteService.getAllPacientes();
    }

    //Obtener todas las atenciones médicas de un paciente por su rut
    @GetMapping("/{rut}")
    public ResponseEntity<Object> getAtencionesMedicasByRut(@PathVariable String rut){
        if (!validarRut(rut)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"Rut no válido."));
        }
        var response = pacienteService.getPacienteByRut(rut);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false,"El rut ingresado no existe."));
        }

        return ResponseEntity.ok(response);
    }

    //Crear atención médica
    @PostMapping
    public ResponseEntity<Object> createPaciente(@RequestBody @Valid PacienteDto Paciente){
        var response = pacienteService.createPaciente(Paciente);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Actualizar atención médica
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePaciente(@PathVariable Integer id, @RequestBody @Valid PacienteDto Paciente){
        var response = pacienteService.updatePaciente(id, Paciente);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private boolean validarRut(String rut) {
        return rut != null && rut.matches("\\d{7,8}-[\\dkK]");
    }
}
