package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.SeguimientoPaquete} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SeguimientoPaqueteDTO implements Serializable {

    private Long id;

    @NotNull
    private String estado;

    @NotNull
    private Instant fecha;

    private String ubicacion;

    private String observaciones;

    private PaqueteDTO paquete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(o instanceof SeguimientoPaqueteDTO)) {
            return false;
        }

        SeguimientoPaqueteDTO seguimientoPaqueteDTO = (SeguimientoPaqueteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, seguimientoPaqueteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeguimientoPaqueteDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", paquete=" + getPaquete() +
            "}";
    }
}
