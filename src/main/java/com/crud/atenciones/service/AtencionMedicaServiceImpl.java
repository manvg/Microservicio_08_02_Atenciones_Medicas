package com.crud.atenciones.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.atenciones.model.AtencionMedica;
import com.crud.atenciones.repository.AtencionMedicaRepository;

@Service
public class AtencionMedicaServiceImpl implements AtencionMedicaService{
    @Autowired
    private AtencionMedicaRepository atencionMedicaRepository;

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
        return atencionMedicaRepository.save(atencionMedica);
    }
}
