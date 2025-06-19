package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.ReportePDF} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportePDFDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipo;

    @NotNull
    private String nombreArchivo;

    private String rutaArchivo;

    @NotNull
    private Instant fechaGeneracion;

    private PaqueteDTO paquete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public Instant getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Instant fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
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
        if (!(o instanceof ReportePDFDTO)) {
            return false;
        }

        ReportePDFDTO reportePDFDTO = (ReportePDFDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportePDFDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportePDFDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", nombreArchivo='" + getNombreArchivo() + "'" +
            ", rutaArchivo='" + getRutaArchivo() + "'" +
            ", fechaGeneracion='" + getFechaGeneracion() + "'" +
            ", paquete=" + getPaquete() +
            "}";
    }
}
