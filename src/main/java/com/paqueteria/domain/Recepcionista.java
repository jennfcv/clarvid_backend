package com.paqueteria.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

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
    @Size(min = 1, max = 50)
    @Column(name = "ci", length = 50, nullable = false, unique = true)
    private String ci;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Size(max = 255)
    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "fecha_ingreso")
    private Instant fechaIngreso;

    @Size(max = 500)
    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(name = "usuario_id", unique = true)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    // Getters y Setters (métodos fluidos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Recepcionista id(Long id) { this.setId(id); return this; }

    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }
    public Recepcionista ci(String ci) { this.setCi(ci); return this; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Recepcionista telefono(String telefono) { this.setTelefono(telefono); return this; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Recepcionista direccion(String direccion) { this.setDireccion(direccion); return this; }

    public Instant getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Instant fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public Recepcionista fechaIngreso(Instant fechaIngreso) { this.setFechaIngreso(fechaIngreso); return this; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Recepcionista observaciones(String observaciones) { this.setObservaciones(observaciones); return this; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Recepcionista nombre(String nombre) { this.setNombre(nombre); return this; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Recepcionista apellido(String apellido) { this.setApellido(apellido); return this; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
    public Recepcionista usuario(User usuario) { this.setUsuario(usuario); return this; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
    public Recepcionista sucursal(Sucursal sucursal) { this.setSucursal(sucursal); return this; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recepcionista)) return false;
        return getId() != null && getId().equals(((Recepcionista) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Recepcionista{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", fechaIngreso=" + getFechaIngreso() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
