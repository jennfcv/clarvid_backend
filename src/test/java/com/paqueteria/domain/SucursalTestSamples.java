package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SucursalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sucursal getSucursalSample1() {
        return new Sucursal().id(1L).nombre("nombre1").direccion("direccion1").pais("pais1").ciudad("ciudad1").telefono("telefono1");
    }

    public static Sucursal getSucursalSample2() {
        return new Sucursal().id(2L).nombre("nombre2").direccion("direccion2").pais("pais2").ciudad("ciudad2").telefono("telefono2");
    }

    public static Sucursal getSucursalRandomSampleGenerator() {
        return new Sucursal()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .pais(UUID.randomUUID().toString())
            .ciudad(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString());
    }
}
