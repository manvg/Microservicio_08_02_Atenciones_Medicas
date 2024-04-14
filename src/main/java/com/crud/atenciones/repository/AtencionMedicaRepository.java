package com.crud.atenciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.model.entities.Paciente;

import java.time.LocalDate;
import java.util.List;


public interface AtencionMedicaRepository extends JpaRepository<AtencionMedica, Integer>{
    List<AtencionMedica> findByPaciente(Paciente paciente);
    List<AtencionMedica> findByFechaAtencionBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
