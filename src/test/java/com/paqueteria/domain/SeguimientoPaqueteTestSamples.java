package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SeguimientoPaqueteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SeguimientoPaquete getSeguimientoPaqueteSample1() {
        return new SeguimientoPaquete().id(1L).estado("estado1").ubicacion("ubicacion1").observaciones("observaciones1");
    }

    public static SeguimientoPaquete getSeguimientoPaqueteSample2() {
        return new SeguimientoPaquete().id(2L).estado("estado2").ubicacion("ubicacion2").observaciones("observaciones2");
    }

    public static SeguimientoPaquete getSeguimientoPaqueteRandomSampleGenerator() {
        return new SeguimientoPaquete()
            .id(longCount.incrementAndGet())
            .estado(UUID.randomUUID().toString())
            .ubicacion(UUID.randomUUID().toString())
            .observaciones(UUID.randomUUID().toString());
    }
}
