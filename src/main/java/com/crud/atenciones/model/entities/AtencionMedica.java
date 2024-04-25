package com.crud.atenciones.model.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Validated
@Table(name = "atencion_medica")
public class AtencionMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion_medica")
    private int idAtencionMedica;

    @Column(name = "especialidad")
    @NotNull
    @Size(min = 5, max = 150, message = "Debe tener entre 5 y 100 caracteres")
    private String especialidad;

    @Column(name = "nombre_medico")
    @NotNull
    @Size(min = 5, max = 150, message = "Debe tener entre 5 y 150 caracteres")
    private String nombreMedico;

    @Column(name = "diagnostico")
    @NotNull
    @Size(min = 5, max = 250, message = "Debe tener entre 5 y 250 caracteres")
    private String diagnostico;

    @Column(name = "tratamiento")
    @NotNull
    @Size(min = 5, max = 250, message = "Debe tener entre 5 y 250 caracteres")
    private String tratamiento;

    @Column(name = "fecha_atencion")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAtencion;

    @ManyToOne(targetEntity = Paciente.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_paciente")
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

    public LocalDate getFechaAtencion(){
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDate fechaAtencion){
        this.fechaAtencion = fechaAtencion;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
