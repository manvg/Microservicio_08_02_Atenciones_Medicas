package com.crud.atenciones.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.model.entities.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AtencionMedicaRepositoryTest {
    @Autowired
    private AtencionMedicaRepository atencionMedicaRepository;

    private AtencionMedica nuevaAtencionMedica;
    private AtencionMedica atencionMedica2;
    private Paciente paciente1;
    private Paciente paciente2;
    @BeforeEach
    public void InicializarPruebas(){
        //Inicializar Pacientes
        paciente1 = new Paciente();
        paciente1.setApellidoMaterno("Guerra");
        paciente1.setApellidoPaterno("Valdés");
        paciente1.setGenero("M");
        paciente1.setNombre("Manuel");
        paciente1.setRut("10200300-1");

        paciente2 = new Paciente();
        paciente2.setApellidoMaterno("Aguirre");
        paciente2.setApellidoPaterno("Gonzalez");
        paciente2.setGenero("M");
        paciente2.setNombre("Pedro");
        paciente2.setRut("15111322-9");

        //Inicializar Atenciones Medicas
        nuevaAtencionMedica = new AtencionMedica();
        nuevaAtencionMedica.setDiagnostico("Estres laboral");
        nuevaAtencionMedica.setEspecialidad("Medicina General");
        nuevaAtencionMedica.setFechaAtencion(LocalDate.now());
        nuevaAtencionMedica.setMedicoAtencion("Juan Perez");
        nuevaAtencionMedica.setTratamiento("Vacaciones permanentes en el caribe");
        nuevaAtencionMedica.setPaciente(paciente1);

        atencionMedica2 = new AtencionMedica();
        atencionMedica2.setDiagnostico("Covid 19");
        atencionMedica2.setEspecialidad("Medicina General");
        atencionMedica2.setFechaAtencion(LocalDate.now());
        atencionMedica2.setMedicoAtencion("Fernanda Muñoz");
        atencionMedica2.setPaciente(paciente2);
        atencionMedica2.setTratamiento("Cuarentena por 14 dias");

    }
    @Test
    @DisplayName("Guardar nueva atención médica => Respuesta correcta")
    public void guardarAtencionMedicaTest() {
        AtencionMedica resultado = atencionMedicaRepository.save(nuevaAtencionMedica);

        assertNotNull(resultado.getIdAtencionMedica());
        assertEquals("Estres laboral", resultado.getDiagnostico());
    }
}
