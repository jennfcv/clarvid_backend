package com.paqueteria.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class RepartidorDTO implements Serializable {

    private Long id;

    @NotNull
    private String ci;

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    private String telefono;
    private String direccion;
    private Boolean disponible;
    private Instant fechaIngreso;
    private String medioTransporte;
    private String email; // Nuevo campo para el email del usuario

    private UserDTO usuario;
    private ZonaEntregaDTO zona;
    private SucursalDTO sucursal;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Instant getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Instant fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getMedioTransporte() { return medioTransporte; }
    public void setMedioTransporte(String medioTransporte) { this.medioTransporte = medioTransporte; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UserDTO getUsuario() { return usuario; }
    public void setUsuario(UserDTO usuario) { this.usuario = usuario; }

    public ZonaEntregaDTO getZona() { return zona; }
    public void setZona(ZonaEntregaDTO zona) { this.zona = zona; }

    public SucursalDTO getSucursal() { return sucursal; }
    public void setSucursal(SucursalDTO sucursal) { this.sucursal = sucursal; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepartidorDTO)) return false;
        return Objects.equals(id, ((RepartidorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RepartidorDTO{" +
            "id=" + id +
            ", ci='" + ci + '\'' +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", telefono='" + telefono + '\'' +
            ", direccion='" + direccion + '\'' +
            ", disponible=" + disponible +
            ", fechaIngreso=" + fechaIngreso +
            ", medioTransporte='" + medioTransporte + '\'' +
            ", email='" + email + '\'' +
            ", usuario=" + usuario +
            ", zona=" + zona +
            ", sucursal=" + sucursal +
            '}';
    }
}
