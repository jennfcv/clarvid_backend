package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RepartidorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Repartidor getRepartidorSample1() {
        return new Repartidor().id(1L).ci("ci1").telefono("telefono1").direccion("direccion1").medioTransporte("medioTransporte1");
    }

    public static Repartidor getRepartidorSample2() {
        return new Repartidor().id(2L).ci("ci2").telefono("telefono2").direccion("direccion2").medioTransporte("medioTransporte2");
    }

    public static Repartidor getRepartidorRandomSampleGenerator() {
        return new Repartidor()
            .id(longCount.incrementAndGet())
            .ci(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .medioTransporte(UUID.randomUUID().toString());
    }
}
