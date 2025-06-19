package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IntentoEntregaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IntentoEntrega getIntentoEntregaSample1() {
        return new IntentoEntrega().id(1L).resultado("resultado1").observaciones("observaciones1");
    }

    public static IntentoEntrega getIntentoEntregaSample2() {
        return new IntentoEntrega().id(2L).resultado("resultado2").observaciones("observaciones2");
    }

    public static IntentoEntrega getIntentoEntregaRandomSampleGenerator() {
        return new IntentoEntrega()
            .id(longCount.incrementAndGet())
            .resultado(UUID.randomUUID().toString())
            .observaciones(UUID.randomUUID().toString());
    }
}
