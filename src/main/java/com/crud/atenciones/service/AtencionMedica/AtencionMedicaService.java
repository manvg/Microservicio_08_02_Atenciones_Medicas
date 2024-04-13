package com.crud.atenciones.service.AtencionMedica;

import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.AtencionMedica;

public interface AtencionMedicaService {
    List<AtencionMedica> getAllAtencionesMedicas();
    Optional<AtencionMedica> getAtencionMedicaById(Integer id);
    AtencionMedica createAtencionMedica(AtencionMedica atencionMedica);
}
