package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.UbicacionRepartidor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UbicacionRepartidorDTO implements Serializable {

    private Long id;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;

    @NotNull
    private Instant fechaHora;

    private RepartidorDTO repartidor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public RepartidorDTO getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(RepartidorDTO repartidor) {
        this.repartidor = repartidor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UbicacionRepartidorDTO)) {
            return false;
        }

        UbicacionRepartidorDTO ubicacionRepartidorDTO = (UbicacionRepartidorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ubicacionRepartidorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UbicacionRepartidorDTO{" +
            "id=" + getId() +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", repartidor=" + getRepartidor() +
            "}";
    }
}
