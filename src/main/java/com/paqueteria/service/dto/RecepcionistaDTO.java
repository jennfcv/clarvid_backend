package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.Recepcionista} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecepcionistaDTO implements Serializable {

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    private Long id;

    @NotNull
    private String ci;

    @NotNull
    private String email;

    private String telefono;

    private String direccion;

    private Instant fechaIngreso;

    private String observaciones;

    private SucursalDTO sucursal;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCi() { return ci; }

    public void setCi(String ci) { this.ci = ci; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Instant getFechaIngreso() { return fechaIngreso; }

    public void setFechaIngreso(Instant fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getObservaciones() { return observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public SucursalDTO getSucursal() { return sucursal; }

    public void setSucursal(SucursalDTO sucursal) { this.sucursal = sucursal; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecepcionistaDTO)) return false;
        RecepcionistaDTO that = (RecepcionistaDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RecepcionistaDTO{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", ci='" + ci + '\'' +
            ", email='" + email + '\'' +
            ", telefono='" + telefono + '\'' +
            ", direccion='" + direccion + '\'' +
            ", fechaIngreso=" + fechaIngreso +
            ", observaciones='" + observaciones + '\'' +
            ", sucursal=" + sucursal +
            '}';
    }
}
