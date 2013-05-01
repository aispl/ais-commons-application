package pl.ais.commons.application.service;

import java.security.Principal;

import com.google.common.base.Function;

/**
 * Defines the API contract for principal transformer.
 *
 * @param <T> determines the type of values returned by this transformer
 * @author Warlock, AIS.PL
 * @since 1.0.3
 */
public interface PrincipalTransformer<T> extends Function<Principal, T> {

    /**
     * Returns the type returned by this transformer.
     *
     * @return the type returned by this transformer
     */
    Class<? extends T> getReturnType();

}
