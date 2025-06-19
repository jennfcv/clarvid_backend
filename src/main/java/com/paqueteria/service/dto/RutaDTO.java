package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.Ruta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RutaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String descripcion;

    private Boolean activa;

    private ZonaEntregaDTO zona;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public ZonaEntregaDTO getZona() {
        return zona;
    }

    public void setZona(ZonaEntregaDTO zona) {
        this.zona = zona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RutaDTO)) {
            return false;
        }

        RutaDTO rutaDTO = (RutaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rutaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RutaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", activa='" + getActiva() + "'" +
            ", zona=" + getZona() +
            "}";
    }
}
