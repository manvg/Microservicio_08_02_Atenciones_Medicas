package com.crud.atenciones.service;

import java.util.List;

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
}
