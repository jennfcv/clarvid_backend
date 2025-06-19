package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.Sucursal} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SucursalDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String direccion;

    @NotNull
    private String pais;

    @NotNull
    private String ciudad;

    private String telefono;

    private Double latitud;

    private Double longitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SucursalDTO)) {
            return false;
        }

        SucursalDTO sucursalDTO = (SucursalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sucursalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SucursalDTO{" +
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
