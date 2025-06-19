package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZonaEntregaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ZonaEntrega getZonaEntregaSample1() {
        return new ZonaEntrega().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static ZonaEntrega getZonaEntregaSample2() {
        return new ZonaEntrega().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static ZonaEntrega getZonaEntregaRandomSampleGenerator() {
        return new ZonaEntrega()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
