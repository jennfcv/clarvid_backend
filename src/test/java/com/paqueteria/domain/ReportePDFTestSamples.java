package com.paqueteria.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReportePDFTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ReportePDF getReportePDFSample1() {
        return new ReportePDF().id(1L).tipo("tipo1").nombreArchivo("nombreArchivo1").rutaArchivo("rutaArchivo1");
    }

    public static ReportePDF getReportePDFSample2() {
        return new ReportePDF().id(2L).tipo("tipo2").nombreArchivo("nombreArchivo2").rutaArchivo("rutaArchivo2");
    }

    public static ReportePDF getReportePDFRandomSampleGenerator() {
        return new ReportePDF()
            .id(longCount.incrementAndGet())
            .tipo(UUID.randomUUID().toString())
            .nombreArchivo(UUID.randomUUID().toString())
            .rutaArchivo(UUID.randomUUID().toString());
    }
}
