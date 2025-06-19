package com.paqueteria.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.CalificacionEntrega} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalificacionEntregaDTO implements Serializable {

    private Long id;

    private Integer puntaje;

    private String comentario;

    private Instant fechaCalificacion;

    private PaqueteDTO paquete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(Instant fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
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
        if (!(o instanceof CalificacionEntregaDTO)) {
            return false;
        }

        CalificacionEntregaDTO calificacionEntregaDTO = (CalificacionEntregaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calificacionEntregaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalificacionEntregaDTO{" +
            "id=" + getId() +
            ", puntaje=" + getPuntaje() +
            ", comentario='" + getComentario() + "'" +
            ", fechaCalificacion='" + getFechaCalificacion() + "'" +
            ", paquete=" + getPaquete() +
            "}";
    }
}
