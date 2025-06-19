package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.ConfirmacionEntrega} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfirmacionEntregaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fechaConfirmacion;

    private String nombreReceptor;

    private String ciReceptor;

    private String observaciones;

    private String fotoReceptor;

    private PaqueteDTO paquete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(Instant fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getCiReceptor() {
        return ciReceptor;
    }

    public void setCiReceptor(String ciReceptor) {
        this.ciReceptor = ciReceptor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFotoReceptor() {
        return fotoReceptor;
    }

    public void setFotoReceptor(String fotoReceptor) {
        this.fotoReceptor = fotoReceptor;
    }

    public PaqueteDTO getPaquete() {
        return paquete;
    }

    public void setPaquete(PaqueteDTO paquete) {
        this.paquete = paquete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfirmacionEntregaDTO)) {
            return false;
        }

        ConfirmacionEntregaDTO confirmacionEntregaDTO = (ConfirmacionEntregaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, confirmacionEntregaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfirmacionEntregaDTO{" +
            "id=" + getId() +
            ", fechaConfirmacion='" + getFechaConfirmacion() + "'" +
            ", nombreReceptor='" + getNombreReceptor() + "'" +
            ", ciReceptor='" + getCiReceptor() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", fotoReceptor='" + getFotoReceptor() + "'" +
            ", paquete=" + getPaquete() +
            "}";
    }
}
