package com.paqueteria.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Sucursal.
 */
@Entity
@Table(name = "sucursal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "pais", nullable = false)
    private String pais;

    @NotNull
    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sucursal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Sucursal nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Sucursal direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return this.pais;
    }

    public Sucursal pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Sucursal ciudad(String ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Sucursal telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public Sucursal latitud(Double latitud) {
        this.setLatitud(latitud);
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public Sucursal longitud(Double longitud) {
        this.setLongitud(longitud);
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sucursal)) {
            return false;
        }
        return getId() != null && getId().equals(((Sucursal) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sucursal{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", pais='" + getPais() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
