package com.crud.atenciones.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "atencion_medica")
public class AtencionMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion_medica")
    @NotNull
    private int idAtencionMedica;

    @Column(name = "especialidad")
    @NotNull
    private String especialidad;

    @Column(name = "nombre_medico")
    @NotNull
    private String nombreMedico;

    @Column(name = "diagnostico")
    @NotNull
    private String diagnostico;

    @Column(name = "tratamiento")
    @NotNull
    private String tratamiento;

    @ManyToOne(targetEntity = Paciente.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_paciente")
    @NotNull
    private Paciente paciente;

    public int getIdAtencionMedica() {
        return idAtencionMedica;
    }

    public void setIdAtencionMedica(int idAtencionMedica) {
        this.idAtencionMedica = idAtencionMedica;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setMedicoAtencion(String nombreMedico) {
        this.nombreMedico = nombreMedico;
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
