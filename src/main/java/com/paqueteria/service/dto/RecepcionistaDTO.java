package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.Recepcionista} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecepcionistaDTO implements Serializable {

    private Long id;

    @NotNull
    private String ci;

    private String telefono;

    private String direccion;

    private Instant fechaIngreso;

    private String observaciones;

    @NotNull
    private UserDTO usuario;

    private SucursalDTO sucursal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Instant getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Instant fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public UserDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UserDTO usuario) {
        this.usuario = usuario;
    }

    public SucursalDTO getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalDTO sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecepcionistaDTO)) {
            return false;
        }

        RecepcionistaDTO recepcionistaDTO = (RecepcionistaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recepcionistaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecepcionistaDTO{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", usuario=" + getUsuario() +
            ", sucursal=" + getSucursal() +
            "}";
    }
}
