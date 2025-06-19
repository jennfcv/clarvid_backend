package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.IntentoEntrega} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntentoEntregaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fechaIntento;

    @NotNull
    private String resultado;

    private String observaciones;

    private Instant proximoIntento;

    private PaqueteDTO paquete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaIntento() {
        return fechaIntento;
    }

    public void setFechaIntento(Instant fechaIntento) {
        this.fechaIntento = fechaIntento;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Instant getProximoIntento() {
        return proximoIntento;
    }

    public void setProximoIntento(Instant proximoIntento) {
        this.proximoIntento = proximoIntento;
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
        if (!(o instanceof IntentoEntregaDTO)) {
            return false;
        }

        IntentoEntregaDTO intentoEntregaDTO = (IntentoEntregaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, intentoEntregaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntentoEntregaDTO{" +
            "id=" + getId() +
            ", fechaIntento='" + getFechaIntento() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", proximoIntento='" + getProximoIntento() + "'" +
            ", paquete=" + getPaquete() +
            "}";
    }
}
