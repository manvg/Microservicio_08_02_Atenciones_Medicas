package com.crud.atenciones.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.crud.atenciones.model.entities.AtencionMedica;
import com.crud.atenciones.model.entities.Paciente;
import com.crud.atenciones.service.AtencionMedica.AtencionMedicaService;

@WebMvcTest(AtencionMedicaController.class)//Referencia al controlador principal
public class AtencionMedicaControllerTest {
    //Configuraciones de los Mocks para simular las solicitudes HTTP al controlador
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtencionMedicaService atencionMedicaServiceMock;

    private AtencionMedica atencionMedica1;
    private AtencionMedica atencionMedica2;
    private Paciente paciente1;
    private Paciente paciente2;
     //Se ejecuta antes de cada metodo de pruebas
    @BeforeEach
    public void InicializarPruebas(){
        //Inicializar Pacientes
        paciente1 = new Paciente();
        paciente1.setIdPaciente(1);
        paciente1.setApellidoMaterno("Guerra");
        paciente1.setApellidoPaterno("Valdés");
        paciente1.setGenero("M");
        paciente1.setNombre("Manuel");
        paciente1.setRut("10200300-1");

        paciente2 = new Paciente();
        paciente2.setIdPaciente(2);
        paciente2.setApellidoMaterno("Aguirre");
        paciente2.setApellidoPaterno("Gonzalez");
        paciente2.setGenero("M");
        paciente2.setNombre("Pedro");
        paciente2.setRut("15111322-9");

        //Inicializar Atenciones Medicas
        atencionMedica1 = new AtencionMedica();
        atencionMedica1.setIdAtencionMedica(1);
        atencionMedica1.setDiagnostico("Estres laboral");
        atencionMedica1.setEspecialidad("Medicina General");
        atencionMedica1.setFechaAtencion(LocalDate.now());
        atencionMedica1.setMedicoAtencion("Juan Perez");
        atencionMedica1.setTratamiento("Vacaciones permanentes en el caribe");
        atencionMedica1.setPaciente(paciente1);

        atencionMedica2 = new AtencionMedica();
        atencionMedica2.setIdAtencionMedica(2);
        atencionMedica2.setDiagnostico("Covid 19");
        atencionMedica2.setEspecialidad("Medicina General");
        atencionMedica2.setFechaAtencion(LocalDate.now());
        atencionMedica2.setMedicoAtencion("Fernanda Muñoz");
        atencionMedica2.setPaciente(paciente2);
        atencionMedica2.setTratamiento("Cuarentena por 14 dias");

    }

    @Test
    @DisplayName("Obtener todas las atenciones medicas => Respuesta correcta")
    public void getAllAtencionesMedicasTest() throws Exception{

        List<AtencionMedica> atenciones = List.of(atencionMedica1, atencionMedica2);
        when(atencionMedicaServiceMock.getAllAtencionesMedicas()).thenReturn(atenciones);

        mockMvc.perform(get("/atenciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.atencionMedicaList.length()").value(2))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].idAtencionMedica").value(1))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].especialidad").value("Medicina General"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].nombreMedico").value("Juan Perez"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].diagnostico").value("Estres laboral"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].tratamiento").value("Vacaciones permanentes en el caribe"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].fechaAtencion").value("2024-05-04"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].paciente.idPaciente").value(1))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].paciente.rut").value("10200300-1"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].paciente.nombre").value("Manuel"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].paciente.apellidoPaterno").value("Valdés"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].paciente.apellidoMaterno").value("Guerra"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0].paciente.genero").value("M"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[0]._links.self.href").value("http://localhost/atenciones/1"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].idAtencionMedica").value(2))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].especialidad").value("Medicina General"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].nombreMedico").value("Fernanda Muñoz"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].diagnostico").value("Covid 19"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].tratamiento").value("Cuarentena por 14 dias"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].fechaAtencion").value("2024-05-04"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].paciente.idPaciente").value(2))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].paciente.rut").value("15111322-9"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].paciente.nombre").value("Pedro"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].paciente.apellidoPaterno").value("Gonzalez"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].paciente.apellidoMaterno").value("Aguirre"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1].paciente.genero").value("M"))
                .andExpect(jsonPath("$._embedded.atencionMedicaList[1]._links.self.href").value("http://localhost/atenciones/2"))
                ;
    }
    
}
