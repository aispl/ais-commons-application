package pl.ais.commons.application.validation;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Verifies {@link AbstractSmartValidator} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class AbstractSmartValidatorExpectations {

    /**
     * Verifies if valid entity instance is accepted by the validator.
     */
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    @Test
    public void shouldAcceptValidEntity() {

        // Given validator applicable to some type, and instance of this type, which should pass the validation ...
        final Validator validator = new ExampleValidator();
        final ExampleEntity entity = new ExampleEntity();
        entity.setProperty("value");

        // ... when we validate the instance, ...
        final Errors errors = Mockito.mock(Errors.class);
        validator.validate(entity, errors);

        // ... then instance should be accepted by the validator.
        Mockito.verify(errors, Mockito.never()).reject(anyString(), any(Object[].class), anyString());
    }

    /**
     * Verifies if validation hints are accepted by the validator.
     */
    @Test
    public void shouldAcceptValidationHints() {

        // Given validator applicable to some type, and instance of this type, which shouldn't pass the validation ...
        final SmartValidator validator = new ExampleValidator();
        final ExampleEntity entity = new ExampleEntity();

        // ... when we validate the instance with 'optional' validation hint, ...
        final Errors errors = Mockito.mock(Errors.class);
        validator.validate(entity, errors, OptionalHint.class);

        // ... then instance should be accepted by the validator.
        Mockito.verify(errors, Mockito.never()).reject(anyString(), any(Object[].class), anyString());
    }

    /**
     * Verifies if invalid entity instance is rejected by the validator.
     */
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    @Test
    public void shouldRejectInvalidEntity() {

        // Given validator applicable to some type, and instance of this type, which shouldn't pass the validation ...
        final Validator validator = new ExampleValidator();
        final ExampleEntity entity = new ExampleEntity();

        // ... when we validate the instance, ...
        final Errors errors = Mockito.mock(Errors.class);
        validator.validate(entity, errors);

        // ... then instance should be rejected by the validator.
        Mockito.verify(errors, Mockito.atLeastOnce()).reject(anyString(), any(Object[].class), anyString());
    }

    /**
     * Verifies if unsupported type is rejected by the validator.
     */
    @Test
    public void shouldRejectUnsupportedType() {

        // Given validator applicable to some type, ...
        final Validator validator = new ExampleValidator();

        // ... this type should be supported by the validator.
        assertFalse("Unsupported type should be rejected by the validator.",
            validator.supports(UnsupportedEntity.class));
    }

    /**
     * Verifies if target type is supported by the validator.
     */
    @Test
    public void shouldSupportTargetType() {

        // Given validator applicable to some type, ...
        final Validator validator = new ExampleValidator();

        // ... this type should be supported by the validator.
        assertTrue("Target type should be supported by the validator.", validator.supports(ExampleEntity.class));
    }

    /**
     * Verifies if validators created as anonymous class properly supports target entities.
     */
    @Test
    public void shouldSupportTargetTypeForAnonymousValidator() {
        final Validator validator = new AbstractSmartValidator<ExampleEntity>() {

            @Override
            protected void validateEntity(@Nonnull final ExampleEntity entity, @Nonnull final Errors errors, final Object... validationHints) {
                // Do nothing ...
            }
        };

        assertTrue("Validator should support target type.", validator.supports(ExampleEntity.class));
    }

    /**
     * Verifies if target type's subclass is supported by the validator.
     */
    @Test
    public void shouldSupportTargetTypeSubclass() {

        // Given validator applicable to some type, ...
        final Validator validator = new ExampleValidator();

        // ... this type should be supported by the validator.
        assertTrue("Target type's subclass should be supported by the validator.",
            validator.supports(ExampleEntitySubclass.class));
    }

    private interface OptionalHint {

        //Empty by design ...
    }

    /**
     * Example entity to be validated.
     */
    private class ExampleEntity {

        private String property;

        /**
         * @return the property
         */
        public String getProperty() {
            return property;
        }

        /**
         * @param property the property to set
         */
        public void setProperty(final String property) {
            this.property = property;
        }

    }

    /**
     * Example entity to be validated.
     */
    @SuppressWarnings("PMD.AtLeastOneConstructor")
    private class ExampleEntitySubclass extends ExampleEntity {

        // Empty by design ...
    }

    /**
     * Example validator.
     */
    private class ExampleValidator extends AbstractSmartValidator<ExampleEntity> {

        /**
         * {@inheritDoc}
         */
        @Override
        protected void validateEntity(final ExampleEntity entity, final Errors errors, final Object... validationHints) {
            final boolean mandatory = !Arrays.asList(validationHints).contains(OptionalHint.class);
            if (mandatory && (null == entity.getProperty())) {
                errors.reject("required", new Object[] {}, "Property cannot be null.");
            }
        }
    }

    private class UnsupportedEntity {

        private Long value;

        /**
         * @return the value
         */
        public Long getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(final Long value) {
            this.value = value;
        }

    }

}
