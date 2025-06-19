package com.paqueteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A CalificacionEntrega.
 */
@Entity
@Table(name = "calificacion_entrega")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalificacionEntrega implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "puntaje")
    private Integer puntaje;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha_calificacion")
    private Instant fechaCalificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "recepcionista", "repartidor", "remitente", "destinatario", "ruta", "sucursalOrigen", "sucursalDestino" },
        allowSetters = true
    )
    private Paquete paquete;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CalificacionEntrega id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntaje() {
        return this.puntaje;
    }

    public CalificacionEntrega puntaje(Integer puntaje) {
        this.setPuntaje(puntaje);
        return this;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return this.comentario;
    }

    public CalificacionEntrega comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getFechaCalificacion() {
        return this.fechaCalificacion;
    }

    public CalificacionEntrega fechaCalificacion(Instant fechaCalificacion) {
        this.setFechaCalificacion(fechaCalificacion);
        return this;
    }

    public void setFechaCalificacion(Instant fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public Paquete getPaquete() {
        return this.paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public CalificacionEntrega paquete(Paquete paquete) {
        this.setPaquete(paquete);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalificacionEntrega)) {
            return false;
        }
        return getId() != null && getId().equals(((CalificacionEntrega) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalificacionEntrega{" +
            "id=" + getId() +
            ", puntaje=" + getPuntaje() +
            ", comentario='" + getComentario() + "'" +
            ", fechaCalificacion='" + getFechaCalificacion() + "'" +
            "}";
    }
}
