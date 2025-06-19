package com.paqueteria.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Recepcionista.
 */
@Entity
@Table(name = "recepcionista")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recepcionista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ci", nullable = false)
    private String ci;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_ingreso")
    private Instant fechaIngreso;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recepcionista id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCi() {
        return this.ci;
    }

    public Recepcionista ci(String ci) {
        this.setCi(ci);
        return this;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Recepcionista telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Recepcionista direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Instant getFechaIngreso() {
        return this.fechaIngreso;
    }

    public Recepcionista fechaIngreso(Instant fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(Instant fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Recepcionista observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public User getUsuario() {
        return this.usuario;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Recepcionista usuario(User user) {
        this.setUsuario(user);
        return this;
    }

    public Sucursal getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Recepcionista sucursal(Sucursal sucursal) {
        this.setSucursal(sucursal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recepcionista)) {
            return false;
        }
        return getId() != null && getId().equals(((Recepcionista) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recepcionista{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
