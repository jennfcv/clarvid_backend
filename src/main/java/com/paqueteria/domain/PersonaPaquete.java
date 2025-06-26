package com.paqueteria.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A PersonaPaquete.
 */
@Entity
@Table(name = "persona_paquete")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonaPaquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ci", nullable = false)
    private String ci;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "email")
    private String email;

    // Agregar getters y setters
    public String getEmail() {
        return this.email;
    }

    public PersonaPaquete email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonaPaquete id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCi() {
        return this.ci;
    }

    public PersonaPaquete ci(String ci) {
        this.setCi(ci);
        return this;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return this.nombre;
    }

    public PersonaPaquete nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public PersonaPaquete telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public PersonaPaquete direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonaPaquete)) {
            return false;
        }
        return getId() != null && getId().equals(((PersonaPaquete) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonaPaquete{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
