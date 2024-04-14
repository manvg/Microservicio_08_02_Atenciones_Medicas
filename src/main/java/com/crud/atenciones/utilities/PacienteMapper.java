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

    public PacienteDto convertirADTO(Paciente atencionMedica) {
        return modelMapper.map(atencionMedica, PacienteDto.class);
    }

    public Paciente convertirAEntity(PacienteDto atencionMedicaDto){
        return modelMapper.map(atencionMedicaDto, Paciente.class);
    }
}
