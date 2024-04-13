package com.crud.atenciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.atenciones.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Integer>{

}
