package com.crud.atenciones.service.Paciente;

import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.Paciente;

public interface PacienteService {
    List<Paciente> getAllPacientes();
    Optional<Paciente> getPacienteById(Integer id);
}