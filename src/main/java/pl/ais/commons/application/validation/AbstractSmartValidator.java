package pl.ais.commons.application.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.ParameterizedType;

/**
 * Class to be extended by smart validators.
 *
 * @param <E> the type of entities supported by this validator
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@ThreadSafe
public abstract class AbstractSmartValidator<E> implements SmartValidator {

    protected final Class<E> entityClass;

    /**
     * Constructs new instance.
     */
    public AbstractSmartValidator() {
        super();
        entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return entityClass.isAssignableFrom(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public final void validate(@Nullable final Object target, @Nonnull final Errors errors) {
        validateEntity((E) target, errors);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public final void validate(@Nullable final Object target, @Nonnull final Errors errors,
                               @Nonnull final Object... validationHints) {
        validateEntity((E) target, errors, validationHints);
    }

    /**
     * Validates given entity.
     *
     * @param entity          the entity that is to be validated
     * @param errors          contextual state about the validation process
     * @param validationHints one or more hint objects to be passed to the validation engine
     */
    protected abstract void validateEntity(@Nonnull final E entity, @Nonnull final Errors errors,
                                           final Object... validationHints);

}
