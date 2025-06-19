package com.paqueteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A SeguimientoPaquete.
 */
@Entity
@Table(name = "seguimiento_paquete")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SeguimientoPaquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "observaciones")
    private String observaciones;

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

    public SeguimientoPaquete id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return this.estado;
    }

    public SeguimientoPaquete estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public SeguimientoPaquete fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public SeguimientoPaquete ubicacion(String ubicacion) {
        this.setUbicacion(ubicacion);
        return this;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public SeguimientoPaquete observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Paquete getPaquete() {
        return this.paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public SeguimientoPaquete paquete(Paquete paquete) {
        this.setPaquete(paquete);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeguimientoPaquete)) {
            return false;
        }
        return getId() != null && getId().equals(((SeguimientoPaquete) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeguimientoPaquete{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
