package com.paqueteria.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfirmacionEntregaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfirmacionEntregaAllPropertiesEquals(ConfirmacionEntrega expected, ConfirmacionEntrega actual) {
        assertConfirmacionEntregaAutoGeneratedPropertiesEquals(expected, actual);
        assertConfirmacionEntregaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfirmacionEntregaAllUpdatablePropertiesEquals(ConfirmacionEntrega expected, ConfirmacionEntrega actual) {
        assertConfirmacionEntregaUpdatableFieldsEquals(expected, actual);
        assertConfirmacionEntregaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfirmacionEntregaAutoGeneratedPropertiesEquals(ConfirmacionEntrega expected, ConfirmacionEntrega actual) {
        assertThat(actual)
            .as("Verify ConfirmacionEntrega auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfirmacionEntregaUpdatableFieldsEquals(ConfirmacionEntrega expected, ConfirmacionEntrega actual) {
        assertThat(actual)
            .as("Verify ConfirmacionEntrega relevant properties")
            .satisfies(a -> assertThat(a.getFechaConfirmacion()).as("check fechaConfirmacion").isEqualTo(expected.getFechaConfirmacion()))
            .satisfies(a -> assertThat(a.getNombreReceptor()).as("check nombreReceptor").isEqualTo(expected.getNombreReceptor()))
            .satisfies(a -> assertThat(a.getCiReceptor()).as("check ciReceptor").isEqualTo(expected.getCiReceptor()))
            .satisfies(a -> assertThat(a.getObservaciones()).as("check observaciones").isEqualTo(expected.getObservaciones()))
            .satisfies(a -> assertThat(a.getFotoReceptor()).as("check fotoReceptor").isEqualTo(expected.getFotoReceptor()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertConfirmacionEntregaUpdatableRelationshipsEquals(ConfirmacionEntrega expected, ConfirmacionEntrega actual) {
        assertThat(actual)
            .as("Verify ConfirmacionEntrega relationships")
            .satisfies(a -> assertThat(a.getPaquete()).as("check paquete").isEqualTo(expected.getPaquete()));
    }
}
