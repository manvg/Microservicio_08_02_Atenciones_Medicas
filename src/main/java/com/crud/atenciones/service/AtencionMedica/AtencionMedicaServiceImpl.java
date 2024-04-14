package com.crud.atenciones.service.AtencionMedica;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.atenciones.model.dto.AtencionMedicaDto;
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
    public List<AtencionMedicaDto> getAtencionesMedicasByRut(String rutPaciente){
        //Obtener pacienet por rut
        var paciente = pacienteRepository.findByRut(rutPaciente);
        if (paciente.isEmpty()) {
            return null;
        }
        var atencionesMedicas = atencionMedicaRepository.findByPaciente(paciente.get());
        return atencionesMedicas.stream().map(atencionMedicaMapper::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public AtencionMedica createAtencionMedica(AtencionMedicaDto atencionMedicaDto){
        var pacienteExistente = pacienteRepository.findByRut(atencionMedicaDto.getPaciente().getRut());
        if (pacienteExistente.isEmpty()) {
            AtencionMedica atencionMedica = atencionMedicaMapper.convertirAEntity(atencionMedicaDto);
            //Si el paciente no existe guarda Atencion Medica y Paciente nuevos
            return atencionMedicaRepository.save(atencionMedica);
        }else{
            AtencionMedica atencionMedica = atencionMedicaMapper.convertirAEntity(atencionMedicaDto);

            AtencionMedica nuevaAtencionMedica = new AtencionMedica();
            nuevaAtencionMedica.setDiagnostico(atencionMedica.getDiagnostico());
            nuevaAtencionMedica.setEspecialidad(atencionMedica.getEspecialidad());
            nuevaAtencionMedica.setMedicoAtencion(atencionMedica.getNombreMedico());
            nuevaAtencionMedica.setTratamiento(atencionMedica.getTratamiento());
            
            // Obtener el paciente de la atención médica
            Paciente paciente = atencionMedica.getPaciente();
            if (paciente == null) {
                paciente = new Paciente();
            }

            // Actualizar los campos del paciente
            paciente.setIdPaciente(pacienteExistente.get().getIdPaciente());
            paciente.setRut(atencionMedica.getPaciente().getRut());
            paciente.setNombre(atencionMedica.getPaciente().getNombre());
            paciente.setApellidoPaterno(atencionMedica.getPaciente().getApellidoPaterno());
            paciente.setApellidoMaterno(atencionMedica.getPaciente().getApellidoMaterno());
            paciente.setGenero(atencionMedica.getPaciente().getGenero());

            // Guardar o actualizar el paciente
            paciente = pacienteRepository.save(paciente);
            
            nuevaAtencionMedica.setPaciente(paciente);

            return atencionMedicaRepository.save(nuevaAtencionMedica);
        }
    }

    @Override
    @Transactional
    public AtencionMedica updateAtencionMedica(Integer id, AtencionMedicaDto atencionMedica){
        if (atencionMedicaRepository.existsById(id)) {
             // Obtener la atención médica existente    por ID
             var atencionExiste = atencionMedicaRepository.findById(id);
             if (!atencionExiste.isEmpty()) {
                AtencionMedica atencion = atencionExiste.get();
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

