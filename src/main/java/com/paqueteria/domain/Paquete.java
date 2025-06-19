package com.paqueteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.paqueteria.domain.enumeration.EstadoPaquete;
import com.paqueteria.domain.enumeration.TipoEntrega;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Paquete.
 */
@Entity
@Table(name = "paquete")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo_seguimiento", nullable = false)
    private String codigoSeguimiento;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "detalle")
    private String detalle;

    @NotNull
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull
    @Column(name = "fecha_envio", nullable = false)
    private Instant fechaEnvio;

    @Column(name = "fecha_entrega_estimada")
    private Instant fechaEntregaEstimada;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPaquete estado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entrega", nullable = false)
    private TipoEntrega tipoEntrega;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    @Column(name = "latitud_entrega")
    private Double latitudEntrega;

    @Column(name = "longitud_entrega")
    private Double longitudEntrega;

    @Column(name = "confirmado")
    private Boolean confirmado;

    @Column(name = "codigo_qr")
    private String codigoQR;

    @Column(name = "ubicacion_actual")
    private String ubicacionActual;

    @Column(name = "fragil")
    private Boolean fragil;

    @Column(name = "cliente_token_acceso")
    private String clienteTokenAcceso;

    @ManyToOne(fetch = FetchType.LAZY)
    private User recepcionista;

    @ManyToOne(fetch = FetchType.LAZY)
    private User repartidor;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonaPaquete remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonaPaquete destinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "zona" }, allowSetters = true)
    private Ruta ruta;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursalOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursalDestino;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paquete id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoSeguimiento() {
        return this.codigoSeguimiento;
    }

    public Paquete codigoSeguimiento(String codigoSeguimiento) {
        this.setCodigoSeguimiento(codigoSeguimiento);
        return this;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Paquete descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public Paquete detalle(String detalle) {
        this.setDetalle(detalle);
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getPeso() {
        return this.peso;
    }

    public Paquete peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Instant getFechaEnvio() {
        return this.fechaEnvio;
    }

    public Paquete fechaEnvio(Instant fechaEnvio) {
        this.setFechaEnvio(fechaEnvio);
        return this;
    }

    public void setFechaEnvio(Instant fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Instant getFechaEntregaEstimada() {
        return this.fechaEntregaEstimada;
    }

    public Paquete fechaEntregaEstimada(Instant fechaEntregaEstimada) {
        this.setFechaEntregaEstimada(fechaEntregaEstimada);
        return this;
    }

    public void setFechaEntregaEstimada(Instant fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public EstadoPaquete getEstado() {
        return this.estado;
    }

    public Paquete estado(EstadoPaquete estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    public TipoEntrega getTipoEntrega() {
        return this.tipoEntrega;
    }

    public Paquete tipoEntrega(TipoEntrega tipoEntrega) {
        this.setTipoEntrega(tipoEntrega);
        return this;
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getDireccionEntrega() {
        return this.direccionEntrega;
    }

    public Paquete direccionEntrega(String direccionEntrega) {
        this.setDireccionEntrega(direccionEntrega);
        return this;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public Double getLatitudEntrega() {
        return this.latitudEntrega;
    }

    public Paquete latitudEntrega(Double latitudEntrega) {
        this.setLatitudEntrega(latitudEntrega);
        return this;
    }

    public void setLatitudEntrega(Double latitudEntrega) {
        this.latitudEntrega = latitudEntrega;
    }

    public Double getLongitudEntrega() {
        return this.longitudEntrega;
    }

    public Paquete longitudEntrega(Double longitudEntrega) {
        this.setLongitudEntrega(longitudEntrega);
        return this;
    }

    public void setLongitudEntrega(Double longitudEntrega) {
        this.longitudEntrega = longitudEntrega;
    }

    public Boolean getConfirmado() {
        return this.confirmado;
    }

    public Paquete confirmado(Boolean confirmado) {
        this.setConfirmado(confirmado);
        return this;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getCodigoQR() {
        return this.codigoQR;
    }

    public Paquete codigoQR(String codigoQR) {
        this.setCodigoQR(codigoQR);
        return this;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getUbicacionActual() {
        return this.ubicacionActual;
    }

    public Paquete ubicacionActual(String ubicacionActual) {
        this.setUbicacionActual(ubicacionActual);
        return this;
    }

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public Boolean getFragil() {
        return this.fragil;
    }

    public Paquete fragil(Boolean fragil) {
        this.setFragil(fragil);
        return this;
    }

    public void setFragil(Boolean fragil) {
        this.fragil = fragil;
    }

    public String getClienteTokenAcceso() {
        return this.clienteTokenAcceso;
    }

    public Paquete clienteTokenAcceso(String clienteTokenAcceso) {
        this.setClienteTokenAcceso(clienteTokenAcceso);
        return this;
    }

    public void setClienteTokenAcceso(String clienteTokenAcceso) {
        this.clienteTokenAcceso = clienteTokenAcceso;
    }

    public User getRecepcionista() {
        return this.recepcionista;
    }

    public void setRecepcionista(User user) {
        this.recepcionista = user;
    }

    public Paquete recepcionista(User user) {
        this.setRecepcionista(user);
        return this;
    }

    public User getRepartidor() {
        return this.repartidor;
    }

    public void setRepartidor(User user) {
        this.repartidor = user;
    }

    public Paquete repartidor(User user) {
        this.setRepartidor(user);
        return this;
    }

    public PersonaPaquete getRemitente() {
        return this.remitente;
    }

    public void setRemitente(PersonaPaquete personaPaquete) {
        this.remitente = personaPaquete;
    }

    public Paquete remitente(PersonaPaquete personaPaquete) {
        this.setRemitente(personaPaquete);
        return this;
    }

    public PersonaPaquete getDestinatario() {
        return this.destinatario;
    }

    public void setDestinatario(PersonaPaquete personaPaquete) {
        this.destinatario = personaPaquete;
    }

    public Paquete destinatario(PersonaPaquete personaPaquete) {
        this.setDestinatario(personaPaquete);
        return this;
    }

    public Ruta getRuta() {
        return this.ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Paquete ruta(Ruta ruta) {
        this.setRuta(ruta);
        return this;
    }

    public Sucursal getSucursalOrigen() {
        return this.sucursalOrigen;
    }

    public void setSucursalOrigen(Sucursal sucursal) {
        this.sucursalOrigen = sucursal;
    }

    public Paquete sucursalOrigen(Sucursal sucursal) {
        this.setSucursalOrigen(sucursal);
        return this;
    }

    public Sucursal getSucursalDestino() {
        return this.sucursalDestino;
    }

    public void setSucursalDestino(Sucursal sucursal) {
        this.sucursalDestino = sucursal;
    }

    public Paquete sucursalDestino(Sucursal sucursal) {
        this.setSucursalDestino(sucursal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paquete)) {
            return false;
        }
        return getId() != null && getId().equals(((Paquete) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paquete{" +
            "id=" + getId() +
            ", codigoSeguimiento='" + getCodigoSeguimiento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", peso=" + getPeso() +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", fechaEntregaEstimada='" + getFechaEntregaEstimada() + "'" +
            ", estado='" + getEstado() + "'" +
            ", tipoEntrega='" + getTipoEntrega() + "'" +
            ", direccionEntrega='" + getDireccionEntrega() + "'" +
            ", latitudEntrega=" + getLatitudEntrega() +
            ", longitudEntrega=" + getLongitudEntrega() +
            ", confirmado='" + getConfirmado() + "'" +
            ", codigoQR='" + getCodigoQR() + "'" +
            ", ubicacionActual='" + getUbicacionActual() + "'" +
            ", fragil='" + getFragil() + "'" +
            ", clienteTokenAcceso='" + getClienteTokenAcceso() + "'" +
            "}";
    }
}
