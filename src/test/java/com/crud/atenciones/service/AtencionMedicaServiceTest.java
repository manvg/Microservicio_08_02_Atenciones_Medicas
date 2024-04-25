package com.crud.atenciones.service;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.repository.AtencionMedicaRepository;
import com.crud.atenciones.service.AtencionMedica.AtencionMedicaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AtencionMedicaServiceTest {
    @InjectMocks
    private AtencionMedicaServiceImpl atencionMedicaServicio;

    @Mock
    private AtencionMedicaRepository atencionMedicaRepositoryMock;

    private AtencionMedica atencionMedica1;
    @BeforeEach
    public void InicializarPruebas(){
        //Inicializar Atenciones Medicas
        atencionMedica1 = new AtencionMedica();

    }
    @Test
    @DisplayName("Actualizar atención medica que no existe en la base de datos. Como resultado debe retornar NULL y en consecuencia un mensaje de que no existe la atención medica")
    public void actualizarAtencionMedicaInexistenteTest() {
        Integer idAtencionMedicaUpdate = 100;
    
        var resultado = atencionMedicaServicio.updateAtencionMedica(idAtencionMedicaUpdate, atencionMedica1);
        assertNull(resultado, "La atención médica id " + idAtencionMedicaUpdate + " no existe");
    }

}
