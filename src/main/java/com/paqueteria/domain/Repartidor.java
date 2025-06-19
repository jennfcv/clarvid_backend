package com.paqueteria.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Repartidor.
 */
@Entity
@Table(name = "repartidor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Repartidor implements Serializable {

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

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "fecha_ingreso")
    private Instant fechaIngreso;

    @Column(name = "medio_transporte")
    private String medioTransporte;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private ZonaEntrega zona;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Repartidor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCi() {
        return this.ci;
    }

    public Repartidor ci(String ci) {
        this.setCi(ci);
        return this;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Repartidor telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Repartidor direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getDisponible() {
        return this.disponible;
    }

    public Repartidor disponible(Boolean disponible) {
        this.setDisponible(disponible);
        return this;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Instant getFechaIngreso() {
        return this.fechaIngreso;
    }

    public Repartidor fechaIngreso(Instant fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(Instant fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getMedioTransporte() {
        return this.medioTransporte;
    }

    public Repartidor medioTransporte(String medioTransporte) {
        this.setMedioTransporte(medioTransporte);
        return this;
    }

    public void setMedioTransporte(String medioTransporte) {
        this.medioTransporte = medioTransporte;
    }

    public User getUsuario() {
        return this.usuario;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Repartidor usuario(User user) {
        this.setUsuario(user);
        return this;
    }

    public ZonaEntrega getZona() {
        return this.zona;
    }

    public void setZona(ZonaEntrega zonaEntrega) {
        this.zona = zonaEntrega;
    }

    public Repartidor zona(ZonaEntrega zonaEntrega) {
        this.setZona(zonaEntrega);
        return this;
    }

    public Sucursal getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Repartidor sucursal(Sucursal sucursal) {
        this.setSucursal(sucursal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Repartidor)) {
            return false;
        }
        return getId() != null && getId().equals(((Repartidor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Repartidor{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", disponible='" + getDisponible() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", medioTransporte='" + getMedioTransporte() + "'" +
            "}";
    }
}
