package com.paqueteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ReportePDF.
 */
@Entity
@Table(name = "reporte_pdf")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportePDF implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    @Column(name = "ruta_archivo")
    private String rutaArchivo;

    @NotNull
    @Column(name = "fecha_generacion", nullable = false)
    private Instant fechaGeneracion;

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

    public ReportePDF id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public ReportePDF tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public ReportePDF nombreArchivo(String nombreArchivo) {
        this.setNombreArchivo(nombreArchivo);
        return this;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return this.rutaArchivo;
    }

    public ReportePDF rutaArchivo(String rutaArchivo) {
        this.setRutaArchivo(rutaArchivo);
        return this;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public Instant getFechaGeneracion() {
        return this.fechaGeneracion;
    }

    public ReportePDF fechaGeneracion(Instant fechaGeneracion) {
        this.setFechaGeneracion(fechaGeneracion);
        return this;
    }

    public void setFechaGeneracion(Instant fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Paquete getPaquete() {
        return this.paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public ReportePDF paquete(Paquete paquete) {
        this.setPaquete(paquete);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportePDF)) {
            return false;
        }
        return getId() != null && getId().equals(((ReportePDF) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportePDF{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", nombreArchivo='" + getNombreArchivo() + "'" +
            ", rutaArchivo='" + getRutaArchivo() + "'" +
            ", fechaGeneracion='" + getFechaGeneracion() + "'" +
            "}";
    }
}
