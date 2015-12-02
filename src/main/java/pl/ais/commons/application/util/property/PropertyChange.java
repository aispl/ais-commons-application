package pl.ais.commons.application.util.property;

import java.io.Serializable;
import java.util.Objects;

/**
 * Property change.
 *
 * @param <T> determines the type of property value
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class PropertyChange<T> implements Serializable {

    private static final long serialVersionUID = 2173534255623440138L;

    private T finalValue;

    private T initialValue;

    private String propertyName;

    /**
     * Constructs new instance.
     *
     * @param propertyName
     * @param initialValue
     * @param finalValue
     */
    public PropertyChange(final String propertyName, final T initialValue, final T finalValue) {
        super();
        this.propertyName = propertyName;
        this.initialValue = initialValue;
        this.finalValue = finalValue;
    }

    /**
     * @return the finalValue
     */
    public T getFinalValue() {
        return finalValue;
    }

    /**
     * @return the initialValue
     */
    public T getInitialValue() {
        return initialValue;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @return {@code true} if initial value equals final value, {@code false} otherwise
     */
    public boolean isIllusory() {
        return Objects.equals(initialValue, finalValue);
    }

    /**
     * @param finalValue the finalValue to set
     */
    public void setFinalValue(final T finalValue) {
        this.finalValue = finalValue;
    }

    /**
     * @param initialValue the initialValue to set
     */
    public void setInitialValue(final T initialValue) {
        this.initialValue = initialValue;
    }

    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName(final String propertyName) {
        this.propertyName = propertyName;
    }

}
