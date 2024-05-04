package com.crud.atenciones.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.model.entities.Paciente;

//Pruebas capa de acceso a datos
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//evita sustitución por una BD embebida. Asegura que la BD sea la misma que la configurada para el entorno de pruebas.
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
        nuevaAtencionMedica.setFechaAtencion(LocalDate.of(2024, 5, 1));
        nuevaAtencionMedica.setMedicoAtencion("Juan Perez");
        nuevaAtencionMedica.setTratamiento("Vacaciones permanentes en el caribe");
        nuevaAtencionMedica.setPaciente(paciente1);

        atencionMedica2 = new AtencionMedica();
        atencionMedica2.setDiagnostico("Covid 19");
        atencionMedica2.setEspecialidad("Medicina General");
        atencionMedica2.setFechaAtencion(LocalDate.of(2024, 5, 2));
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

    @Test
    @DisplayName("Buscar atenciones médicas por rango de fechas => Respuesta correcta")
    public void buscarAtencionesPorRangoDeFechasTest() {
        atencionMedicaRepository.save(nuevaAtencionMedica);
        atencionMedicaRepository.save(atencionMedica2);

        LocalDate fechaInicio = LocalDate.of(2024, 5, 1);
        LocalDate fechaFin = LocalDate.of(2024, 5, 2);

        List<AtencionMedica> atencionesPorFechas = atencionMedicaRepository.findByFechaAtencionBetween(fechaInicio, fechaFin);

        assertFalse(atencionesPorFechas.isEmpty());

        assertTrue(atencionesPorFechas.stream().anyMatch(a -> {
            LocalDate fechaAtencion = a.getFechaAtencion();
            return fechaAtencion.isAfter(fechaInicio) && fechaAtencion.isBefore(fechaFin) || fechaAtencion.isEqual(fechaInicio) || fechaAtencion.isEqual(fechaFin);
        }));
    }

}
