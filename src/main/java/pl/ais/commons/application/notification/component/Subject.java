package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Notification subject.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@Immutable
public final class Subject implements Serializable {

    private static final String DEFAULT_CHARSET = "US-ASCII";

    private static final long serialVersionUID = 554772880997777359L;

    private final String charsetName;

    private final String value;

    /**
     * Constructs new instance.
     *
     * @param value       the subject
     * @param charsetName name of the charset used for subject encoding
     */
    private Subject(@Nonnull final String value, @Nonnull final String charsetName) {
        super();

        Objects.requireNonNull(value, "Subject is required.");
        Objects.requireNonNull(charsetName, "Charset name is required.");

        this.charsetName = charsetName;
        this.value = value;
    }

    /**
     * Creates and returns new {@link Subject} instance enclosing given subject.
     *
     * <p><strong>Note:</strong> this method assumes that given subject is encoded using '{@value #DEFAULT_CHARSET}'
     * encoding</p>
     *
     * @param subject the subject
     * @return newly created {@link Subject} instance enclosing given subject
     */
    public static Subject subject(@Nonnull final String subject) {
        return new Subject(subject, DEFAULT_CHARSET);
    }

    /**
     * Creates and returns new {@link Subject} instance enclosing given subject.
     *
     * @param subject     the subject
     * @param charsetName name of the charset used for encoding subject
     * @return newly created {@link Subject} instance enclosing given subject
     */
    public static Subject subject(@Nonnull final String subject, @Nonnull final String charsetName) {
        return new Subject(subject, charsetName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (object instanceof Subject)) {
            final Subject other = (Subject) object;
            result = Objects.equals(charsetName, other.charsetName) && Objects.equals(value, other.value);
        }
        return result;
    }

    /**
     * @return name of the charset used for subject encoding
     */
    public String getCharsetName() {
        return charsetName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(charsetName, value);
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and validate the state.
        validateState();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return value;
    }

    private void validateState() {
        Objects.requireNonNull(value, "Subject is required.");
        Objects.requireNonNull(charsetName, "Name of the charset is required.");
    }

}
