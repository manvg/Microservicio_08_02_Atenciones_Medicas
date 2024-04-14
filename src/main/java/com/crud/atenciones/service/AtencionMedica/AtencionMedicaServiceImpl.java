package com.crud.atenciones.service.AtencionMedica;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.dto.AtencionMedicaDto;
import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.model.entities.Paciente;
import com.crud.atenciones.repository.AtencionMedicaRepository;
import com.crud.atenciones.repository.PacienteRepository;

import jakarta.transaction.Transactional;

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
    public List<AtencionMedicaDto> getAtencionesMedicasByRangoFecha(LocalDate fechaInicio, LocalDate fechaFin){
        List<AtencionMedica> atencionesMedicas = atencionMedicaRepository.findByFechaAtencionBetween(fechaInicio, fechaFin);
        return atencionesMedicas.stream().map(atencionMedicaMapper::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public List<AtencionMedicaDto> getAtencionesMedicasByRut(String rutPaciente){
        var paciente = pacienteRepository.findByRut(rutPaciente);
        if (paciente.isEmpty()) {
            return null;
        }
        var atencionesMedicas = atencionMedicaRepository.findByPaciente(paciente.get());
        return atencionesMedicas.stream().map(atencionMedicaMapper::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public ResponseModel createAtencionMedica(AtencionMedicaDto atencionMedicaDto){
        var pacienteExistente = pacienteRepository.findByRut(atencionMedicaDto.getPaciente().getRut());
        if (pacienteExistente.isEmpty()) {
            //Mapea DTO a entidad Atencion Medica
            AtencionMedica atencionMedica = atencionMedicaMapper.convertirAEntity(atencionMedicaDto);
            atencionMedica.setFechaAtencion(LocalDate.now());
            // Si el paciente no existe guarda Atencion Médica y Paciente nuevos
            var resultado = atencionMedicaRepository.save(atencionMedica);
            return new ResponseModel(true, "Atención médica creada con éxito. Id: " + resultado.getIdAtencionMedica());
        }else{
            //Mapea DTO a entidad Atencion Medica
            AtencionMedica atencionMedica = atencionMedicaMapper.convertirAEntity(atencionMedicaDto);
            AtencionMedica nuevaAtencionMedica = new AtencionMedica();
            nuevaAtencionMedica.setDiagnostico(atencionMedica.getDiagnostico());
            nuevaAtencionMedica.setEspecialidad(atencionMedica.getEspecialidad());
            nuevaAtencionMedica.setMedicoAtencion(atencionMedica.getNombreMedico());
            nuevaAtencionMedica.setTratamiento(atencionMedica.getTratamiento());
            
            //Obtener el paciente de la atención médica
            Paciente paciente = atencionMedica.getPaciente();
            if (paciente == null) {
                paciente = new Paciente();
            }

            //Actualizar los campos del paciente
            paciente.setIdPaciente(pacienteExistente.get().getIdPaciente());
            paciente.setRut(atencionMedica.getPaciente().getRut());
            paciente.setNombre(atencionMedica.getPaciente().getNombre());
            paciente.setApellidoPaterno(atencionMedica.getPaciente().getApellidoPaterno());
            paciente.setApellidoMaterno(atencionMedica.getPaciente().getApellidoMaterno());
            paciente.setGenero(atencionMedica.getPaciente().getGenero());

            //Guardar o actualizar el paciente
            paciente = pacienteRepository.save(paciente);
            
            nuevaAtencionMedica.setPaciente(paciente);
            nuevaAtencionMedica.setFechaAtencion(LocalDate.now());
            var resultado = atencionMedicaRepository.save(nuevaAtencionMedica);
            return new ResponseModel(true, "Atención médica creada con éxito. Id: " + resultado.getIdAtencionMedica());
        }
    }

    @Override
    @Transactional
    public ResponseModel updateAtencionMedica(Integer id, AtencionMedicaDto atencionMedicaDto){
        if (atencionMedicaRepository.existsById(id)) {
             //Obtener la atención médica existente por ID
             var atencionExiste = atencionMedicaRepository.findById(id);
             if (!atencionExiste.isEmpty()) {
                AtencionMedica atencion = atencionExiste.get();
                 //Actualizar los campos de la atención médica
                 atencion.setDiagnostico(atencionMedicaDto.getDiagnostico());
                 atencion.setEspecialidad(atencionMedicaDto.getEspecialidad());
                 atencion.setMedicoAtencion(atencionMedicaDto.getNombreMedico());
                 atencion.setTratamiento(atencionMedicaDto.getTratamiento());
 
                 // Obtener el paciente de la atención médica
                 Paciente paciente = atencion.getPaciente();
                 if (paciente == null) {
                     paciente = new Paciente();
                 }
 
                 // Actualizar los campos del paciente
                 paciente.setRut(atencionMedicaDto.getPaciente().getRut());
                 paciente.setNombre(atencionMedicaDto.getPaciente().getNombre());
                 paciente.setApellidoPaterno(atencionMedicaDto.getPaciente().getApellidoPaterno());
                 paciente.setApellidoMaterno(atencionMedicaDto.getPaciente().getApellidoMaterno());
                 paciente.setGenero(atencionMedicaDto.getPaciente().getGenero());
 
                 //Guardar o actualizar el paciente
                 paciente = pacienteRepository.save(paciente);
 
                 //Asignar el paciente actualizado a la atención médica y guardar
                 atencion.setPaciente(paciente);
                 var resultado = atencionMedicaRepository.save(atencion);

                 return new ResponseModel(true, "Atención médica actualizada con éxito. Id: " + resultado.getIdAtencionMedica());
                }
        }
        return new ResponseModel(false, "La atención médica con id " + id + " no existe");
    }

    @Override
    public void deleteAtencionMedica(Integer id){
        if (atencionMedicaRepository.existsById(id)) {
            atencionMedicaRepository.deleteById(id);
        }
    }

    private Date getDateNow(){
        LocalDate localDate = LocalDate.now();
        return Date.valueOf(localDate);
    }
}

