package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PersonaPaqueteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PersonaPaquete getPersonaPaqueteSample1() {
        return new PersonaPaquete().id(1L).ci("ci1").nombre("nombre1").telefono("telefono1").direccion("direccion1");
    }

    public static PersonaPaquete getPersonaPaqueteSample2() {
        return new PersonaPaquete().id(2L).ci("ci2").nombre("nombre2").telefono("telefono2").direccion("direccion2");
    }

    public static PersonaPaquete getPersonaPaqueteRandomSampleGenerator() {
        return new PersonaPaquete()
            .id(longCount.incrementAndGet())
            .ci(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString());
    }
}
