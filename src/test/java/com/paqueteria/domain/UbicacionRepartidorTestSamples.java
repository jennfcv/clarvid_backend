package com.paqueteria.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class UbicacionRepartidorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UbicacionRepartidor getUbicacionRepartidorSample1() {
        return new UbicacionRepartidor().id(1L);
    }

    public static UbicacionRepartidor getUbicacionRepartidorSample2() {
        return new UbicacionRepartidor().id(2L);
    }

    public static UbicacionRepartidor getUbicacionRepartidorRandomSampleGenerator() {
        return new UbicacionRepartidor().id(longCount.incrementAndGet());
    }
}
