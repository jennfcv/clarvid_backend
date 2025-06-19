package com.paqueteria.service.dto;

import com.paqueteria.domain.enumeration.EstadoPaquete;
import com.paqueteria.domain.enumeration.TipoEntrega;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paqueteria.domain.Paquete} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaqueteDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigoSeguimiento;

    private String descripcion;

    private String detalle;

    @NotNull
    private Double peso;

    @NotNull
    private Instant fechaEnvio;

    private Instant fechaEntregaEstimada;

    @NotNull
    private EstadoPaquete estado;

    @NotNull
    private TipoEntrega tipoEntrega;

    private String direccionEntrega;

    private Double latitudEntrega;

    private Double longitudEntrega;

    private Boolean confirmado;

    private String codigoQR;

    private String ubicacionActual;

    private Boolean fragil;

    private String clienteTokenAcceso;

    private UserDTO recepcionista;

    private UserDTO repartidor;

    private PersonaPaqueteDTO remitente;

    private PersonaPaqueteDTO destinatario;

    private RutaDTO ruta;

    private SucursalDTO sucursalOrigen;

    private SucursalDTO sucursalDestino;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Instant getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Instant fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Instant getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(Instant fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public Double getLatitudEntrega() {
        return latitudEntrega;
    }

    public void setLatitudEntrega(Double latitudEntrega) {
        this.latitudEntrega = latitudEntrega;
    }

    public Double getLongitudEntrega() {
        return longitudEntrega;
    }

    public void setLongitudEntrega(Double longitudEntrega) {
        this.longitudEntrega = longitudEntrega;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public Boolean getFragil() {
        return fragil;
    }

    public void setFragil(Boolean fragil) {
        this.fragil = fragil;
    }

    public String getClienteTokenAcceso() {
        return clienteTokenAcceso;
    }

    public void setClienteTokenAcceso(String clienteTokenAcceso) {
        this.clienteTokenAcceso = clienteTokenAcceso;
    }

    public UserDTO getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(UserDTO recepcionista) {
        this.recepcionista = recepcionista;
    }

    public UserDTO getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(UserDTO repartidor) {
        this.repartidor = repartidor;
    }

    public PersonaPaqueteDTO getRemitente() {
        return remitente;
    }

    public void setRemitente(PersonaPaqueteDTO remitente) {
        this.remitente = remitente;
    }

    public PersonaPaqueteDTO getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(PersonaPaqueteDTO destinatario) {
        this.destinatario = destinatario;
    }

    public RutaDTO getRuta() {
        return ruta;
    }

    public void setRuta(RutaDTO ruta) {
        this.ruta = ruta;
    }

    public SucursalDTO getSucursalOrigen() {
        return sucursalOrigen;
    }

    public void setSucursalOrigen(SucursalDTO sucursalOrigen) {
        this.sucursalOrigen = sucursalOrigen;
    }

    public SucursalDTO getSucursalDestino() {
        return sucursalDestino;
    }

    public void setSucursalDestino(SucursalDTO sucursalDestino) {
        this.sucursalDestino = sucursalDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaqueteDTO)) {
            return false;
        }

        PaqueteDTO paqueteDTO = (PaqueteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paqueteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaqueteDTO{" +
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
            ", recepcionista=" + getRecepcionista() +
            ", repartidor=" + getRepartidor() +
            ", remitente=" + getRemitente() +
            ", destinatario=" + getDestinatario() +
            ", ruta=" + getRuta() +
            ", sucursalOrigen=" + getSucursalOrigen() +
            ", sucursalDestino=" + getSucursalDestino() +
            "}";
    }
}
