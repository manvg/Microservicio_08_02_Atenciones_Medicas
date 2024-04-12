package com.crud.atenciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AtencionMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion_medica")
    private int idAtencionMedica;
    @Column(name = "medico_atencion")
    private String medicoAtencion;
    @Column(name = "diagnostico")
    private String diagnostico;
    @Column(name = "tratamiento")
    private String tratamiento;
    @Column(name = "especialidad")
    private String especialidad;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    public int getIdAtencionMedica() {
        return idAtencionMedica;
    }

    public void setIdAtencionMedica(int idAtencionMedica) {
        this.idAtencionMedica = idAtencionMedica;
    }

    public String getMedicoAtencion() {
        return medicoAtencion;
    }

    public void setMedicoAtencion(String medicoAtencion) {
        this.medicoAtencion = medicoAtencion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
