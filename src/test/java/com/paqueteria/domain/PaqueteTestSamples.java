package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaqueteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Paquete getPaqueteSample1() {
        return new Paquete()
            .id(1L)
            .codigoSeguimiento("codigoSeguimiento1")
            .descripcion("descripcion1")
            .detalle("detalle1")
            .direccionEntrega("direccionEntrega1")
            .codigoQR("codigoQR1")
            .ubicacionActual("ubicacionActual1")
            .clienteTokenAcceso("clienteTokenAcceso1");
    }

    public static Paquete getPaqueteSample2() {
        return new Paquete()
            .id(2L)
            .codigoSeguimiento("codigoSeguimiento2")
            .descripcion("descripcion2")
            .detalle("detalle2")
            .direccionEntrega("direccionEntrega2")
            .codigoQR("codigoQR2")
            .ubicacionActual("ubicacionActual2")
            .clienteTokenAcceso("clienteTokenAcceso2");
    }

    public static Paquete getPaqueteRandomSampleGenerator() {
        return new Paquete()
            .id(longCount.incrementAndGet())
            .codigoSeguimiento(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .detalle(UUID.randomUUID().toString())
            .direccionEntrega(UUID.randomUUID().toString())
            .codigoQR(UUID.randomUUID().toString())
            .ubicacionActual(UUID.randomUUID().toString())
            .clienteTokenAcceso(UUID.randomUUID().toString());
    }
}
