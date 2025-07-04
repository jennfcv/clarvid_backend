package com.paqueteria.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportePDFAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReportePDFAllPropertiesEquals(ReportePDF expected, ReportePDF actual) {
        assertReportePDFAutoGeneratedPropertiesEquals(expected, actual);
        assertReportePDFAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReportePDFAllUpdatablePropertiesEquals(ReportePDF expected, ReportePDF actual) {
        assertReportePDFUpdatableFieldsEquals(expected, actual);
        assertReportePDFUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReportePDFAutoGeneratedPropertiesEquals(ReportePDF expected, ReportePDF actual) {
        assertThat(actual)
            .as("Verify ReportePDF auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReportePDFUpdatableFieldsEquals(ReportePDF expected, ReportePDF actual) {
        assertThat(actual)
            .as("Verify ReportePDF relevant properties")
            .satisfies(a -> assertThat(a.getTipo()).as("check tipo").isEqualTo(expected.getTipo()))
            .satisfies(a -> assertThat(a.getNombreArchivo()).as("check nombreArchivo").isEqualTo(expected.getNombreArchivo()))
            .satisfies(a -> assertThat(a.getRutaArchivo()).as("check rutaArchivo").isEqualTo(expected.getRutaArchivo()))
            .satisfies(a -> assertThat(a.getFechaGeneracion()).as("check fechaGeneracion").isEqualTo(expected.getFechaGeneracion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReportePDFUpdatableRelationshipsEquals(ReportePDF expected, ReportePDF actual) {
        assertThat(actual)
            .as("Verify ReportePDF relationships")
            .satisfies(a -> assertThat(a.getPaquete()).as("check paquete").isEqualTo(expected.getPaquete()));
    }
}
