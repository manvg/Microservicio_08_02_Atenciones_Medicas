package com.crud.atenciones.model.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private int idPaciente;

    @Column(name = "rut")
    @Pattern(regexp = "\\d{7,8}-[\\dkK]", message = "RUT no válido")
    private String rut;

    @Column(name = "nombre")
    @NotNull
    @Size(min = 2, max = 100, message = "Debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Column(name = "apellido_paterno")
    @Size(min = 2, max = 100, message = "Debe tener entre 2 y 100 caracteres")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    @Size(min = 2, max = 100, message = "Debe tener entre 2 y 100 caracteres")
    private String apellidoMaterno;

    @Column(name = "genero")
    @Pattern(regexp = "[MF]", message = "El campo género solo puede ser 'M' o 'F'")
    private String genero;

    @OneToMany(targetEntity = AtencionMedica.class, fetch = FetchType.LAZY, mappedBy = "paciente")
    private List<AtencionMedica> listaAtencionesMedicas;
    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
