package com.paqueteria.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UbicacionRepartidorAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUbicacionRepartidorAllPropertiesEquals(UbicacionRepartidor expected, UbicacionRepartidor actual) {
        assertUbicacionRepartidorAutoGeneratedPropertiesEquals(expected, actual);
        assertUbicacionRepartidorAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUbicacionRepartidorAllUpdatablePropertiesEquals(UbicacionRepartidor expected, UbicacionRepartidor actual) {
        assertUbicacionRepartidorUpdatableFieldsEquals(expected, actual);
        assertUbicacionRepartidorUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUbicacionRepartidorAutoGeneratedPropertiesEquals(UbicacionRepartidor expected, UbicacionRepartidor actual) {
        assertThat(actual)
            .as("Verify UbicacionRepartidor auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUbicacionRepartidorUpdatableFieldsEquals(UbicacionRepartidor expected, UbicacionRepartidor actual) {
        assertThat(actual)
            .as("Verify UbicacionRepartidor relevant properties")
            .satisfies(a -> assertThat(a.getLatitud()).as("check latitud").isEqualTo(expected.getLatitud()))
            .satisfies(a -> assertThat(a.getLongitud()).as("check longitud").isEqualTo(expected.getLongitud()))
            .satisfies(a -> assertThat(a.getFechaHora()).as("check fechaHora").isEqualTo(expected.getFechaHora()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUbicacionRepartidorUpdatableRelationshipsEquals(UbicacionRepartidor expected, UbicacionRepartidor actual) {
        assertThat(actual)
            .as("Verify UbicacionRepartidor relationships")
            .satisfies(a -> assertThat(a.getRepartidor()).as("check repartidor").isEqualTo(expected.getRepartidor()));
    }
}
