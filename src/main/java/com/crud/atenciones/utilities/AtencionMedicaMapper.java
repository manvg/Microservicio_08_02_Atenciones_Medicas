package com.crud.atenciones.utilities;

import org.springframework.stereotype.Component;

import com.crud.atenciones.model.dto.AtencionMedicaDto;
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

    public AtencionMedica convertirAEntity(AtencionMedicaDto atencionMedicaDto){
        return modelMapper.map(atencionMedicaDto, AtencionMedica.class);
    }
}
