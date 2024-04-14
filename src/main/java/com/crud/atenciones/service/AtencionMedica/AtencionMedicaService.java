package com.crud.atenciones.service.AtencionMedica;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.dto.AtencionMedicaDto;
import com.crud.atenciones.model.entities.AtencionMedica;

public interface AtencionMedicaService {
    List<AtencionMedicaDto> getAllAtencionesMedicas();
    List<AtencionMedicaDto> getAtencionesMedicasByRut(String rut);
    List<AtencionMedicaDto> getAtencionesMedicasByRangoFecha(LocalDate fechaInicio, LocalDate fechaFin);
    Optional<AtencionMedicaDto> getAtencionMedicaById(Integer id);
    ResponseModel createAtencionMedica(AtencionMedicaDto atencionMedica);
    ResponseModel updateAtencionMedica(Integer id, AtencionMedicaDto atencionMedica);
    void deleteAtencionMedica(Integer id);
}
