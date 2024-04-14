package com.crud.atenciones.model.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDto {
    private int idPaciente;

    @Pattern(regexp = "\\d{7,8}-[\\dkK]", message = "RUT no válido")
    private String rut;

    @NotNull
    @Size(min = 2, max = 100, message = "Debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(min = 2, max = 100, message = "Debe tener entre 2 y 100 caracteres")
    private String apellidoPaterno;

    @Size(min = 2, max = 100, message = "Debe tener entre 2 y 100 caracteres")
    private String apellidoMaterno;

    @Pattern(regexp = "[MF]", message = "El campo género solo puede ser 'M' o 'F'")
    private String genero;
}
