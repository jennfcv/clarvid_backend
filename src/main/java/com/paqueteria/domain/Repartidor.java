package com.paqueteria.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

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
    @Size(min = 1, max = 50)
    @Column(name = "ci", length = 50, nullable = false, unique = true)
    private String ci;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @NotNull
    @Email
    @Size(min = 5, max = 254)
    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Size(max = 255)
    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "fecha_ingreso")
    private Instant fechaIngreso;

    @Size(max = 50)
    @Column(name = "medio_transporte", length = 50)
    private String medioTransporte;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(name = "usuario_id", unique = true)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_id")
    private ZonaEntrega zona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Repartidor id(Long id) { this.setId(id); return this; }

    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }
    public Repartidor ci(String ci) { this.setCi(ci); return this; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Repartidor nombre(String nombre) { this.setNombre(nombre); return this; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Repartidor apellido(String apellido) { this.setApellido(apellido); return this; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Repartidor email(String email) { this.setEmail(email); return this; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Repartidor telefono(String telefono) { this.setTelefono(telefono); return this; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Repartidor direccion(String direccion) { this.setDireccion(direccion); return this; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    public Repartidor disponible(Boolean disponible) { this.setDisponible(disponible); return this; }

    public Instant getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Instant fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public Repartidor fechaIngreso(Instant fechaIngreso) { this.setFechaIngreso(fechaIngreso); return this; }

    public String getMedioTransporte() { return medioTransporte; }
    public void setMedioTransporte(String medioTransporte) { this.medioTransporte = medioTransporte; }
    public Repartidor medioTransporte(String medioTransporte) { this.setMedioTransporte(medioTransporte); return this; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
    public Repartidor usuario(User usuario) { this.setUsuario(usuario); return this; }

    public ZonaEntrega getZona() { return zona; }
    public void setZona(ZonaEntrega zona) { this.zona = zona; }
    public Repartidor zona(ZonaEntrega zona) { this.setZona(zona); return this; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
    public Repartidor sucursal(Sucursal sucursal) { this.setSucursal(sucursal); return this; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Repartidor)) return false;
        return getId() != null && getId().equals(((Repartidor) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Repartidor{" +
            "id=" + getId() +
            ", ci='" + getCi() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", disponible=" + getDisponible() +
            ", fechaIngreso=" + getFechaIngreso() +
            ", medioTransporte='" + getMedioTransporte() + "'" +
            "}";
    }
}
