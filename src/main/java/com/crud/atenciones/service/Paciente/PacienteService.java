package com.crud.atenciones.service.Paciente;

import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.dto.PacienteDto;
import com.crud.atenciones.model.entities.Paciente;

public interface PacienteService {
    List<PacienteDto> getAllPacientes();
    Optional<Paciente> getPacienteById(Integer id);
    Optional<Paciente> getPacienteByRut(String rut);
    Paciente createPaciente(PacienteDto atencionMedica);
    Paciente updatePaciente(Integer id, PacienteDto paciente);
}