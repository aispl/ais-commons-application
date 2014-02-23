package pl.ais.commons.application.validation.spring;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import com.google.common.reflect.TypeToken;

/**
 * Class to be extended by validators.
 *
 * @param <E> the type of entities supported by this validator
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@ThreadSafe
public class SmartValidatorSupport<E> implements SmartValidator {

    @SuppressWarnings("serial")
    private final TypeToken<E> typeToken = new TypeToken<E>(getClass()) {

        // Empty by design ...
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return typeToken.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(@Nullable final Object target, @Nonnull final Errors errors) {
        if (null != target) {
            validateEntity((E) target, errors);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(@Nullable final Object target, @Nonnull final Errors errors, final Object... validationHints) {
        if (null != target) {
            validateEntity((E) target, errors, validationHints);
        }
    }

    /**
     * Validates given entity.
     *
     * @param entity the entity that is to be validated
     * @param errors contextual state about the validation process
     * @param validationHints one or more hint objects to be passed to the validation engine
     */
    protected void validateEntity(@Nonnull final E entity, @Nonnull final Errors errors,
        final Object... validationHints) {
        // Empty by design ...
    }

}
