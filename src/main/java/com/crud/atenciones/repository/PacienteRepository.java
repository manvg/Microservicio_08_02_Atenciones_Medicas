package com.crud.atenciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.atenciones.model.entities.Paciente;


public interface PacienteRepository extends JpaRepository<Paciente, Integer>{
    Optional<Paciente> findByRut(String rut);
}
