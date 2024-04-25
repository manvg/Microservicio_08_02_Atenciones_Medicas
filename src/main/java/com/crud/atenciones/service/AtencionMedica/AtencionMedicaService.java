package com.crud.atenciones.service.AtencionMedica;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.entities.AtencionMedica;

public interface AtencionMedicaService {
    List<AtencionMedica> getAllAtencionesMedicas();
    List<AtencionMedica> getAtencionesMedicasByRut(String rut);
    List<AtencionMedica> getAtencionesMedicasByRangoFecha(LocalDate fechaInicio, LocalDate fechaFin);
    Optional<AtencionMedica> getAtencionMedicaById(Integer id);
    AtencionMedica createAtencionMedica(AtencionMedica atencionMedica);
    AtencionMedica updateAtencionMedica(Integer id, AtencionMedica atencionMedica);
    ResponseModel deleteAtencionMedica(Integer id);
}
