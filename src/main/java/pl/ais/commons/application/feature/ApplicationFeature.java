package pl.ais.commons.application.feature;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is an Application Feature.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ApplicationFeature {

    /**
     * @return {@code true} if this feature requires exclusive lock on target entity/aggregate,
     *         {@code false} otherwise (this is the default value)
     */
    boolean exclusiveLock() default false;

    /**
     * @return {@code true} if this feature is R/O,
     *         {@code false} if it modifies the domain state (this is the default value).
     */
    boolean readOnly() default false;

}
