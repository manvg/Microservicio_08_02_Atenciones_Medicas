package com.crud.atenciones.service.Paciente;

import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.entities.Paciente;

public interface PacienteService {
    List<Paciente> getAllPacientes();
    Optional<Paciente> getPacienteById(Integer id);
    Optional<Paciente> getPacienteByRut(String rut);
    ResponseModel createPaciente(Paciente atencionMedica);
    ResponseModel updatePaciente(Integer id, Paciente paciente);
    ResponseModel deletePaciente(Integer id);

}