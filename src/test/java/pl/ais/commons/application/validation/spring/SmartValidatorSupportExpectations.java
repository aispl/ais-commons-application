package pl.ais.commons.application.validation.spring;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;

/**
 * Verifies {@link SmartValidatorSupport} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class SmartValidatorSupportExpectations {

    /**
     * Example entity to be validated.
     */
    class ExampleEntity {

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
    class ExampleEntitySubclass extends ExampleEntity {

        // Empty by design ...
    }

    /**
     * Example validator.
     */
    class ExampleValidator extends SmartValidatorSupport<ExampleEntity> {

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

    interface OptionalHint {

        //Empty by design ...
    }

    class UnsupportedEntity {

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

    /**
     * Verifies if validation hints are accepted by the validator.
     */
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    @Test
    public void shouldAcceptValidationHints() {

        // Given validator applicable to some type, and instance of this type, which shouldn't pass the validation ...
        final SmartValidator validator = new ExampleValidator();
        final ExampleEntity entity = new ExampleEntity();

        // ... when we validate the instance with 'optional' validation hint, ...
        final Errors errors = Mockito.mock(Errors.class);
        validator.validate(entity, errors, OptionalHint.class);

        // ... then instance should be accepted by the validator.
        Mockito.verify(errors, Mockito.never()).reject(anyString(), Matchers.any(Object[].class), anyString());
    }

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
        Mockito.verify(errors, Mockito.never()).reject(anyString(), Matchers.any(Object[].class), anyString());
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
        Mockito.verify(errors, Mockito.atLeastOnce()).reject(anyString(), Matchers.any(Object[].class), anyString());
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

}
