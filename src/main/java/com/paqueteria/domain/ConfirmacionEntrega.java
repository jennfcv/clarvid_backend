package com.paqueteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ConfirmacionEntrega.
 */
@Entity
@Table(name = "confirmacion_entrega")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfirmacionEntrega implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_confirmacion", nullable = false)
    private Instant fechaConfirmacion;

    @Column(name = "nombre_receptor")
    private String nombreReceptor;

    @Column(name = "ci_receptor")
    private String ciReceptor;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "foto_receptor")
    private String fotoReceptor;

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

    public ConfirmacionEntrega id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaConfirmacion() {
        return this.fechaConfirmacion;
    }

    public ConfirmacionEntrega fechaConfirmacion(Instant fechaConfirmacion) {
        this.setFechaConfirmacion(fechaConfirmacion);
        return this;
    }

    public void setFechaConfirmacion(Instant fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public String getNombreReceptor() {
        return this.nombreReceptor;
    }

    public ConfirmacionEntrega nombreReceptor(String nombreReceptor) {
        this.setNombreReceptor(nombreReceptor);
        return this;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getCiReceptor() {
        return this.ciReceptor;
    }

    public ConfirmacionEntrega ciReceptor(String ciReceptor) {
        this.setCiReceptor(ciReceptor);
        return this;
    }

    public void setCiReceptor(String ciReceptor) {
        this.ciReceptor = ciReceptor;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public ConfirmacionEntrega observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFotoReceptor() {
        return this.fotoReceptor;
    }

    public ConfirmacionEntrega fotoReceptor(String fotoReceptor) {
        this.setFotoReceptor(fotoReceptor);
        return this;
    }

    public void setFotoReceptor(String fotoReceptor) {
        this.fotoReceptor = fotoReceptor;
    }

    public Paquete getPaquete() {
        return this.paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public ConfirmacionEntrega paquete(Paquete paquete) {
        this.setPaquete(paquete);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfirmacionEntrega)) {
            return false;
        }
        return getId() != null && getId().equals(((ConfirmacionEntrega) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfirmacionEntrega{" +
            "id=" + getId() +
            ", fechaConfirmacion='" + getFechaConfirmacion() + "'" +
            ", nombreReceptor='" + getNombreReceptor() + "'" +
            ", ciReceptor='" + getCiReceptor() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", fotoReceptor='" + getFotoReceptor() + "'" +
            "}";
    }
}
