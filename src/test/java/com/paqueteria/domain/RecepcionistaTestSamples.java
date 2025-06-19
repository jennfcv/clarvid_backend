package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecepcionistaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Recepcionista getRecepcionistaSample1() {
        return new Recepcionista().id(1L).ci("ci1").telefono("telefono1").direccion("direccion1").observaciones("observaciones1");
    }

    public static Recepcionista getRecepcionistaSample2() {
        return new Recepcionista().id(2L).ci("ci2").telefono("telefono2").direccion("direccion2").observaciones("observaciones2");
    }

    public static Recepcionista getRecepcionistaRandomSampleGenerator() {
        return new Recepcionista()
            .id(longCount.incrementAndGet())
            .ci(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .observaciones(UUID.randomUUID().toString());
    }
}
