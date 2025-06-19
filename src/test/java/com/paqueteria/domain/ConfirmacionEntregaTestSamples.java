package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConfirmacionEntregaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ConfirmacionEntrega getConfirmacionEntregaSample1() {
        return new ConfirmacionEntrega()
            .id(1L)
            .nombreReceptor("nombreReceptor1")
            .ciReceptor("ciReceptor1")
            .observaciones("observaciones1")
            .fotoReceptor("fotoReceptor1");
    }

    public static ConfirmacionEntrega getConfirmacionEntregaSample2() {
        return new ConfirmacionEntrega()
            .id(2L)
            .nombreReceptor("nombreReceptor2")
            .ciReceptor("ciReceptor2")
            .observaciones("observaciones2")
            .fotoReceptor("fotoReceptor2");
    }

    public static ConfirmacionEntrega getConfirmacionEntregaRandomSampleGenerator() {
        return new ConfirmacionEntrega()
            .id(longCount.incrementAndGet())
            .nombreReceptor(UUID.randomUUID().toString())
            .ciReceptor(UUID.randomUUID().toString())
            .observaciones(UUID.randomUUID().toString())
            .fotoReceptor(UUID.randomUUID().toString());
    }
}
