package com.crud.atenciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtencionMedicaDto {
    private Integer idAtencionMedica;
    private String especialidad;
    private String nombreMedico;
    private String diagnostico;
    private String tratamiento;
    private PacienteDto paciente;
}
