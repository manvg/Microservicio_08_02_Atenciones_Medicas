package com.crud.atenciones.service.AtencionMedica;

import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.dto.AtencionMedicaDto;
import com.crud.atenciones.model.entities.AtencionMedica;

public interface AtencionMedicaService {
    List<AtencionMedicaDto> getAllAtencionesMedicas();
    List<AtencionMedicaDto> getAtencionesMedicasByRut(String rut);
    Optional<AtencionMedicaDto> getAtencionMedicaById(Integer id);
    AtencionMedica createAtencionMedica(AtencionMedicaDto atencionMedica);
    AtencionMedica updateAtencionMedica(Integer id, AtencionMedicaDto atencionMedica);
    void deleteAtencionMedica(Integer id);
}
