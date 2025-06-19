package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.Repartidor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RepartidorDTO implements Serializable {

    private Long id;

    @NotNull
    private String ci;

    private String telefono;

    private String direccion;

    private Boolean disponible;

    private Instant fechaIngreso;

    private String medioTransporte;

    @NotNull
    private UserDTO usuario;

    private ZonaEntregaDTO zona;

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

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Instant getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Instant fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getMedioTransporte() {
        return medioTransporte;
    }

    public void setMedioTransporte(String medioTransporte) {
        this.medioTransporte = medioTransporte;
    }

    public UserDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UserDTO usuario) {
        this.usuario = usuario;
    }

    public ZonaEntregaDTO getZona() {
        return zona;
    }

    public void setZona(ZonaEntregaDTO zona) {
        this.zona = zona;
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
        if (!(o instanceof RepartidorDTO)) {
            return false;
        }

        RepartidorDTO repartidorDTO = (RepartidorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, repartidorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepartidorDTO{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", disponible='" + getDisponible() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", medioTransporte='" + getMedioTransporte() + "'" +
            ", usuario=" + getUsuario() +
            ", zona=" + getZona() +
            ", sucursal=" + getSucursal() +
            "}";
    }
}
