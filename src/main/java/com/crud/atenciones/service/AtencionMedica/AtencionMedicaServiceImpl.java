package com.crud.atenciones.service.AtencionMedica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.atenciones.model.AtencionMedica;
import com.crud.atenciones.model.Paciente;
import com.crud.atenciones.repository.AtencionMedicaRepository;
import com.crud.atenciones.repository.PacienteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AtencionMedicaServiceImpl implements AtencionMedicaService{
    @Autowired
    private AtencionMedicaRepository atencionMedicaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<AtencionMedica> getAllAtencionesMedicas(){
        return atencionMedicaRepository.findAll();
    }

    @Override
    public Optional<AtencionMedica> getAtencionMedicaById(Integer id){
        return atencionMedicaRepository.findById(id);
    }

    @Override
    public AtencionMedica createAtencionMedica(AtencionMedica atencionMedica){
        if (atencionMedica.getPaciente().getIdPaciente() == 0) {
            return atencionMedicaRepository.save(atencionMedica);
        }else{
            var pacienteExistente = pacienteRepository.findById(atencionMedica.getPaciente().getIdPaciente());

            AtencionMedica nuevaAtencionMedica = new AtencionMedica();
            nuevaAtencionMedica.setDiagnostico(atencionMedica.getDiagnostico());
            nuevaAtencionMedica.setEspecialidad(atencionMedica.getEspecialidad());
            nuevaAtencionMedica.setMedicoAtencion(atencionMedica.getNombreMedico());
            nuevaAtencionMedica.setTratamiento(atencionMedica.getTratamiento());
    
            nuevaAtencionMedica.setPaciente(pacienteExistente.get());

            return atencionMedicaRepository.save(nuevaAtencionMedica);
        }
    }
}
