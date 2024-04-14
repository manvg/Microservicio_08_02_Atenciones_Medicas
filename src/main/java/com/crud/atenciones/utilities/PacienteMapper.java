package com.crud.atenciones.utilities;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.crud.atenciones.model.dto.PacienteDto;
import com.crud.atenciones.model.entities.Paciente;

@Component
public class PacienteMapper {
    private final ModelMapper modelMapper;

    public PacienteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PacienteDto convertirADTO(Paciente pacienteDto) {
        return modelMapper.map(pacienteDto, PacienteDto.class);
    }

    public Paciente convertirAEntity(PacienteDto pacienteDto){
        return modelMapper.map(pacienteDto, Paciente.class);
    }
}
