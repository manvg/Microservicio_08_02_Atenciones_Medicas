package com.crud.atenciones.service.Paciente;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.atenciones.model.ResponseModel;
import com.crud.atenciones.model.dto.PacienteDto;
import com.crud.atenciones.model.entities.Paciente;
import com.crud.atenciones.repository.PacienteRepository;
import com.crud.atenciones.utilities.PacienteMapper;

@Service
public class PacienteServiceImpl implements PacienteService{
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PacienteMapper pacienteMapper;

    //---------GET---------//
    @Override
    public List<PacienteDto> getAllPacientes(){
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(pacienteMapper::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public Optional<Paciente> getPacienteById(Integer id){
        return pacienteRepository.findById(id);
    }

    @Override
    public Optional<Paciente> getPacienteByRut(String rut){
        return pacienteRepository.findByRut(rut);
    }

    //---------CREATE---------//
    @Override
    public ResponseModel createPaciente(PacienteDto pacienteDto){
        Paciente paciente = pacienteMapper.convertirAEntity(pacienteDto);
        var existeRut = pacienteRepository.findByRut(paciente.getRut());
        if (!existeRut.isEmpty()) {
            return new ResponseModel(false, "Ya existe un paciente con el rut " + existeRut.get().getRut());
        }
        var resultado =  pacienteRepository.save(paciente);
        return new ResponseModel(true, "Paciente creado con éxito. Id: " + resultado.getIdPaciente());
    }

    //---------UPDATE---------//
    @Override
    public ResponseModel updatePaciente(Integer id, PacienteDto pacienteDto){
        Paciente paciente = pacienteMapper.convertirAEntity(pacienteDto);
        if (pacienteRepository.existsById(id)) {
            paciente.setIdPaciente(id);
            var resultado = pacienteRepository.save(paciente);
            return new ResponseModel(true, "Paciente actualizado con éxito. Id: " + resultado.getIdPaciente());
        }else{
            return new ResponseModel(false, "El paciente ingresado no existe. No se encontró el id " + id);
        }
    }

    //---------DELETE---------//
    @Override
    public ResponseModel deletePaciente(Integer id){
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            return new ResponseModel(true, "Paciente eliminado con éxito");
        }else{
            return new ResponseModel(false, "El paciente ingresado no existe");
        }
    }

}
