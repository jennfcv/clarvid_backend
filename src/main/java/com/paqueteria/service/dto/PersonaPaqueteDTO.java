package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.PersonaPaquete} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonaPaqueteDTO implements Serializable {

    private Long id;

    @NotNull
    private String ci;

    @NotNull
    private String nombre;

    private String telefono;

    @NotNull
    private String direccion;
    private String email;

    // Agregar getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonaPaqueteDTO)) {
            return false;
        }

        PersonaPaqueteDTO personaPaqueteDTO = (PersonaPaqueteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personaPaqueteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonaPaqueteDTO{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
