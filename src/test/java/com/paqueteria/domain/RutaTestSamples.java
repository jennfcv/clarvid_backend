package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RutaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ruta getRutaSample1() {
        return new Ruta().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Ruta getRutaSample2() {
        return new Ruta().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Ruta getRutaRandomSampleGenerator() {
        return new Ruta().id(longCount.incrementAndGet()).nombre(UUID.randomUUID().toString()).descripcion(UUID.randomUUID().toString());
    }
}
