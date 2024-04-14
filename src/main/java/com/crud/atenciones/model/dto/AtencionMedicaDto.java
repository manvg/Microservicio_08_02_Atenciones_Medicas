package com.crud.atenciones.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class AtencionMedicaDto {
    private Integer idAtencionMedica;

    @NotNull
    @Size(min = 10, max = 150, message = "Debe tener entre 10 y 100 caracteres")
    private String especialidad;

    @NotNull
    @Size(min = 10, max = 150, message = "Debe tener entre 10 y 150 caracteres")
    private String nombreMedico;

    @NotNull
    @Size(min = 10, max = 250, message = "Debe tener entre 10 y 250 caracteres")
    private String diagnostico;

    @NotNull
    @Size(min = 10, max = 250, message = "Debe tener entre 10 y 250 caracteres")
    private String tratamiento;

    @Valid
    private PacienteDto paciente;
}
