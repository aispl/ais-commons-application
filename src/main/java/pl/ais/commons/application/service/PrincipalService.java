package pl.ais.commons.application.service;

import java.security.Principal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the API contract for principal service.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.3
 */
public interface PrincipalService {

    /**
     * Turns principal into its representation as given class.
     *
     * @param principal the principal (may be {@code null})
     * @param asClass the type of desired principal representation
     * @return principal representation
     * @throws IllegalArgumentException if the service doesn't know how to represent the principal as given class
     * @throws NullPointerException if desired principal representation cannot be created for a {@code null} principal
     */
    @Nonnull
    <T> T unwrap(@Nullable Principal principal, @Nonnull Class<T> asClass) throws IllegalArgumentException,
        NullPointerException;

}
