package com.crud.atenciones.service.AtencionMedica;

import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.AtencionMedicaDto;
import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.entities.AtencionMedica;

public interface AtencionMedicaService {
    List<AtencionMedicaDto> getAllAtencionesMedicas();
    List<AtencionMedicaDto> getAtencionesMedicasByRut(String rut);
    Optional<AtencionMedicaDto> getAtencionMedicaById(Integer id);
    AtencionMedica createAtencionMedica(AtencionMedica atencionMedica);
    AtencionMedica updateAtencionMedica(Integer id, AtencionMedica atencionMedica);
    void deleteAtencionMedica(Integer id);
}
