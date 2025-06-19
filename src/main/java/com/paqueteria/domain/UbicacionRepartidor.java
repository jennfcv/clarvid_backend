package com.paqueteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A UbicacionRepartidor.
 */
@Entity
@Table(name = "ubicacion_repartidor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UbicacionRepartidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private Instant fechaHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "usuario", "zona", "sucursal" }, allowSetters = true)
    private Repartidor repartidor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UbicacionRepartidor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public UbicacionRepartidor latitud(Double latitud) {
        this.setLatitud(latitud);
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public UbicacionRepartidor longitud(Double longitud) {
        this.setLongitud(longitud);
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Instant getFechaHora() {
        return this.fechaHora;
    }

    public UbicacionRepartidor fechaHora(Instant fechaHora) {
        this.setFechaHora(fechaHora);
        return this;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Repartidor getRepartidor() {
        return this.repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public UbicacionRepartidor repartidor(Repartidor repartidor) {
        this.setRepartidor(repartidor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UbicacionRepartidor)) {
            return false;
        }
        return getId() != null && getId().equals(((UbicacionRepartidor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UbicacionRepartidor{" +
            "id=" + getId() +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            ", fechaHora='" + getFechaHora() + "'" +
            "}";
    }
}
