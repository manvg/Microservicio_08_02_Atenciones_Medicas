package com.crud.atenciones.utilities;

import org.springframework.stereotype.Component;

import com.crud.atenciones.model.AtencionMedicaDto;
import com.crud.atenciones.model.entities.AtencionMedica;
import org.modelmapper.ModelMapper;

@Component
public class AtencionMedicaMapper {
    private final ModelMapper modelMapper;

    public AtencionMedicaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AtencionMedicaDto convertirADTO(AtencionMedica atencionMedica) {
        return modelMapper.map(atencionMedica, AtencionMedicaDto.class);
    }
}
