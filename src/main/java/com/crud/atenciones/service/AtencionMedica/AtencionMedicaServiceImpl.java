package com.crud.atenciones.service.AtencionMedica;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.atenciones.model.AtencionMedicaDto;
import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.model.entities.Paciente;
import com.crud.atenciones.repository.AtencionMedicaRepository;
import com.crud.atenciones.repository.PacienteRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.crud.atenciones.utilities.*;

@Service
public class AtencionMedicaServiceImpl implements AtencionMedicaService{
    @Autowired
    private AtencionMedicaRepository atencionMedicaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private AtencionMedicaMapper atencionMedicaMapper;
    @Override
    public List<AtencionMedicaDto> getAllAtencionesMedicas(){
        List<AtencionMedica> atencionesMedicas = atencionMedicaRepository.findAll();
        return atencionesMedicas.stream().map(atencionMedicaMapper::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public Optional<AtencionMedicaDto> getAtencionMedicaById(Integer id){
        var atencionMedica = atencionMedicaRepository.findById(id);
        return atencionMedica.stream().map(atencionMedicaMapper::convertirADTO).findFirst();
    }

    @Override
    public AtencionMedica createAtencionMedica(AtencionMedica atencionMedica){
        if (atencionMedica.getPaciente().getIdPaciente() == 0) {
            //Si el paciente no existe guarda Atencion Medica y Paciente nuevos
            return atencionMedicaRepository.save(atencionMedica);
        }else{
            //Valida existencia del paciente
            var pacienteExistente = pacienteRepository.findById(atencionMedica.getPaciente().getIdPaciente());
            if (!pacienteExistente.isEmpty()) {
                AtencionMedica nuevaAtencionMedica = new AtencionMedica();
                nuevaAtencionMedica.setDiagnostico(atencionMedica.getDiagnostico());
                nuevaAtencionMedica.setEspecialidad(atencionMedica.getEspecialidad());
                nuevaAtencionMedica.setMedicoAtencion(atencionMedica.getNombreMedico());
                nuevaAtencionMedica.setTratamiento(atencionMedica.getTratamiento());
        
                nuevaAtencionMedica.setPaciente(pacienteExistente.get());

                return atencionMedicaRepository.save(nuevaAtencionMedica);
            }else{
                //El id paciente ingresado no existe
                return null;
            }
        }
    }

    @Override
    @Transactional
    public AtencionMedica updateAtencionMedica(Integer id, AtencionMedica atencionMedica){
        if (atencionMedicaRepository.existsById(id)) {
             // Obtener la atención médica existente    por ID
             AtencionMedica atencion = atencionMedicaRepository.findById(id).orElse(null);
             if (atencion != null) {
                 // Actualizar los campos de la atención médica
                 atencion.setDiagnostico(atencionMedica.getDiagnostico());
                 atencion.setEspecialidad(atencionMedica.getEspecialidad());
                 atencion.setMedicoAtencion(atencionMedica.getNombreMedico());
                 atencion.setTratamiento(atencionMedica.getTratamiento());
 
                 // Obtener el paciente de la atención médica
                 Paciente paciente = atencion.getPaciente();
                 if (paciente == null) {
                     paciente = new Paciente();
                 }
 
                 // Actualizar los campos del paciente
                 paciente.setRut(atencionMedica.getPaciente().getRut());
                 paciente.setNombre(atencionMedica.getPaciente().getNombre());
                 paciente.setApellidoPaterno(atencionMedica.getPaciente().getApellidoPaterno());
                 paciente.setApellidoMaterno(atencionMedica.getPaciente().getApellidoMaterno());
                 paciente.setGenero(atencionMedica.getPaciente().getGenero());
 
                 // Guardar o actualizar el paciente
                 paciente = pacienteRepository.save(paciente);
 
                 // Asignar el paciente actualizado a la atención médica y guardarla
                 atencion.setPaciente(paciente);
                 return atencionMedicaRepository.save(atencion);
                }else{
                    return null;
                }
        }else{
            return null;
        }
    }

    @Override
    public void deleteAtencionMedica(Integer id){
        if (atencionMedicaRepository.existsById(id)) {
            atencionMedicaRepository.deleteById(id);
        }
    }

}

