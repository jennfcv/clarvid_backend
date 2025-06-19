package com.paqueteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A IntentoEntrega.
 */
@Entity
@Table(name = "intento_entrega")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntentoEntrega implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_intento", nullable = false)
    private Instant fechaIntento;

    @NotNull
    @Column(name = "resultado", nullable = false)
    private String resultado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "proximo_intento")
    private Instant proximoIntento;

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

    public IntentoEntrega id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaIntento() {
        return this.fechaIntento;
    }

    public IntentoEntrega fechaIntento(Instant fechaIntento) {
        this.setFechaIntento(fechaIntento);
        return this;
    }

    public void setFechaIntento(Instant fechaIntento) {
        this.fechaIntento = fechaIntento;
    }

    public String getResultado() {
        return this.resultado;
    }

    public IntentoEntrega resultado(String resultado) {
        this.setResultado(resultado);
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public IntentoEntrega observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Instant getProximoIntento() {
        return this.proximoIntento;
    }

    public IntentoEntrega proximoIntento(Instant proximoIntento) {
        this.setProximoIntento(proximoIntento);
        return this;
    }

    public void setProximoIntento(Instant proximoIntento) {
        this.proximoIntento = proximoIntento;
    }

    public Paquete getPaquete() {
        return this.paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public IntentoEntrega paquete(Paquete paquete) {
        this.setPaquete(paquete);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntentoEntrega)) {
            return false;
        }
        return getId() != null && getId().equals(((IntentoEntrega) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntentoEntrega{" +
            "id=" + getId() +
            ", fechaIntento='" + getFechaIntento() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", proximoIntento='" + getProximoIntento() + "'" +
            "}";
    }
}
