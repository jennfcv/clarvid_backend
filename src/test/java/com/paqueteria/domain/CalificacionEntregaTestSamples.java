package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CalificacionEntregaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CalificacionEntrega getCalificacionEntregaSample1() {
        return new CalificacionEntrega().id(1L).puntaje(1).comentario("comentario1");
    }

    public static CalificacionEntrega getCalificacionEntregaSample2() {
        return new CalificacionEntrega().id(2L).puntaje(2).comentario("comentario2");
    }

    public static CalificacionEntrega getCalificacionEntregaRandomSampleGenerator() {
        return new CalificacionEntrega()
            .id(longCount.incrementAndGet())
            .puntaje(intCount.incrementAndGet())
            .comentario(UUID.randomUUID().toString());
    }
}
