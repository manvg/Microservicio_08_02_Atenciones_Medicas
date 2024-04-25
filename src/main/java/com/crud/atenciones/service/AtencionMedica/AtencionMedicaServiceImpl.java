package com.crud.atenciones.service.AtencionMedica;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.model.entities.Paciente;
import com.crud.atenciones.repository.AtencionMedicaRepository;
import com.crud.atenciones.repository.PacienteRepository;

import jakarta.transaction.Transactional;

@Service
public class AtencionMedicaServiceImpl implements AtencionMedicaService{
    @Autowired
    private AtencionMedicaRepository atencionMedicaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    //----------GET----------//
    @Override
    public List<AtencionMedica> getAllAtencionesMedicas(){
        return atencionMedicaRepository.findAll();
    }

    @Override
    public Optional<AtencionMedica> getAtencionMedicaById(Integer id){
        return atencionMedicaRepository.findById(id);
    }

    @Override
    public List<AtencionMedica> getAtencionesMedicasByRangoFecha(LocalDate fechaInicio, LocalDate fechaFin){
        return atencionMedicaRepository.findByFechaAtencionBetween(fechaInicio, fechaFin);
    }

    @Override
    public List<AtencionMedica> getAtencionesMedicasByRut(String rutPaciente){
        var paciente = pacienteRepository.findByRut(rutPaciente);
        if (paciente.isEmpty()) {
            return null;
        }
        return atencionMedicaRepository.findByPaciente(paciente.get());
    }

    //----------POST----------//
    @Override
    public AtencionMedica createAtencionMedica(AtencionMedica atencionMedicaCreate){
        var pacienteExistente = pacienteRepository.findByRut(atencionMedicaCreate.getPaciente().getRut());
        if (pacienteExistente.isEmpty()) {
            atencionMedicaCreate.setFechaAtencion(LocalDate.now());
            // Si el paciente no existe guarda Atencion Médica y Paciente nuevos
            return atencionMedicaRepository.save(atencionMedicaCreate);
        }else{
            AtencionMedica nuevaAtencionMedica = new AtencionMedica();
            nuevaAtencionMedica.setDiagnostico(atencionMedicaCreate.getDiagnostico());
            nuevaAtencionMedica.setEspecialidad(atencionMedicaCreate.getEspecialidad());
            nuevaAtencionMedica.setMedicoAtencion(atencionMedicaCreate.getNombreMedico());
            nuevaAtencionMedica.setTratamiento(atencionMedicaCreate.getTratamiento());
            
            //Obtener el paciente de la atención médica
            Paciente paciente = atencionMedicaCreate.getPaciente();
            if (paciente == null) {
                paciente = new Paciente();
            }

            //Actualizar los campos del paciente
            paciente.setIdPaciente(pacienteExistente.get().getIdPaciente());
            paciente.setRut(atencionMedicaCreate.getPaciente().getRut());
            paciente.setNombre(atencionMedicaCreate.getPaciente().getNombre());
            paciente.setApellidoPaterno(atencionMedicaCreate.getPaciente().getApellidoPaterno());
            paciente.setApellidoMaterno(atencionMedicaCreate.getPaciente().getApellidoMaterno());
            paciente.setGenero(atencionMedicaCreate.getPaciente().getGenero());

            //Guardar o actualizar el paciente
            paciente = pacienteRepository.save(paciente);
            
            nuevaAtencionMedica.setPaciente(paciente);
            nuevaAtencionMedica.setFechaAtencion(LocalDate.now());
            return atencionMedicaRepository.save(nuevaAtencionMedica);
        }
    }

     //----------PUT----------//
    @Override
    @Transactional
    public AtencionMedica updateAtencionMedica(Integer id, AtencionMedica atencionMedicaUpdate){
        if (atencionMedicaRepository.existsById(id)) {
             //Obtener la atención médica existente por ID
             var atencionExiste = atencionMedicaRepository.findById(id);
             if (!atencionExiste.isEmpty()) {
                AtencionMedica atencion = atencionExiste.get();
                 //Actualizar los campos de la atención médica
                 atencion.setDiagnostico(atencionMedicaUpdate.getDiagnostico());
                 atencion.setEspecialidad(atencionMedicaUpdate.getEspecialidad());
                 atencion.setMedicoAtencion(atencionMedicaUpdate.getNombreMedico());
                 atencion.setTratamiento(atencionMedicaUpdate.getTratamiento());
 
                 // Obtener el paciente de la atención médica
                 Paciente paciente = atencion.getPaciente();
                 if (paciente == null) {
                     paciente = new Paciente();
                 }
 
                 // Actualizar los campos del paciente
                 paciente.setRut(atencionMedicaUpdate.getPaciente().getRut());
                 paciente.setNombre(atencionMedicaUpdate.getPaciente().getNombre());
                 paciente.setApellidoPaterno(atencionMedicaUpdate.getPaciente().getApellidoPaterno());
                 paciente.setApellidoMaterno(atencionMedicaUpdate.getPaciente().getApellidoMaterno());
                 paciente.setGenero(atencionMedicaUpdate.getPaciente().getGenero());
 
                 //Guardar o actualizar el paciente
                 paciente = pacienteRepository.save(paciente);
 
                 //Asignar el paciente actualizado a la atención médica y guardar
                 atencion.setPaciente(paciente);
                 return atencionMedicaRepository.save(atencion);
                }
        }
        return null;
    }

     //----------DELETE----------//
    @Override
    public ResponseModel deleteAtencionMedica(Integer id){
        if (atencionMedicaRepository.existsById(id)) {
            atencionMedicaRepository.deleteById(id);
            return new ResponseModel(true, "Atención médica eliminada con éxito");
        }else{
            return new ResponseModel(false, "La Atención médica ingresada no existe");
        }
    }
}

