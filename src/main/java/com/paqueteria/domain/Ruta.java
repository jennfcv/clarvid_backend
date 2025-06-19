package com.paqueteria.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Ruta.
 */
@Entity
@Table(name = "ruta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activa")
    private Boolean activa;

    @ManyToOne(fetch = FetchType.LAZY)
    private ZonaEntrega zona;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ruta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Ruta nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Ruta descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActiva() {
        return this.activa;
    }

    public Ruta activa(Boolean activa) {
        this.setActiva(activa);
        return this;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public ZonaEntrega getZona() {
        return this.zona;
    }

    public void setZona(ZonaEntrega zonaEntrega) {
        this.zona = zonaEntrega;
    }

    public Ruta zona(ZonaEntrega zonaEntrega) {
        this.setZona(zonaEntrega);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ruta)) {
            return false;
        }
        return getId() != null && getId().equals(((Ruta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ruta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", activa='" + getActiva() + "'" +
            "}";
    }
}
