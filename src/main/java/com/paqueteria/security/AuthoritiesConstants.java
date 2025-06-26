package com.paqueteria.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String RECEPCIONISTA = "ROLE_RECEPCIONISTA";
    public static final String REPARTIDOR = "ROLE_REPARTIDOR";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
