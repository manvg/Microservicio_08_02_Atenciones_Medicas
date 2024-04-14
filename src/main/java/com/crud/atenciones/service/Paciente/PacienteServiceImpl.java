package com.crud.atenciones.service.Paciente;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Paciente createPaciente(PacienteDto pacienteDto){
        Paciente paciente = pacienteMapper.convertirAEntity(pacienteDto);
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente updatePaciente(Integer id, PacienteDto pacienteDto){
        Paciente paciente = pacienteMapper.convertirAEntity(pacienteDto);
        if (pacienteRepository.existsById(id)) {
            paciente.setIdPaciente(id);
            return pacienteRepository.save(paciente);
        }else{
            return null;
        }
    }

}
