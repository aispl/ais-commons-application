package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Typed content.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
abstract class TypedContent implements NotificationComponent, Serializable {

    private static final long serialVersionUID = 7059300599040576656L;

    /**
     * The content type.
     *
     * @serial
     * @since 1.2.1
     */
    private final String contentType;

    TypedContent(@Nonnull final String contentType) {

        // Validate constructor requirements, ...
        Objects.requireNonNull(contentType, "Content type is required");

        // ... and initialize this instance fields.
        this.contentType = contentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (object instanceof TypedContent)) {
            final TypedContent other = (TypedContent) object;
            result = Objects.equals(contentType, other.contentType);
        }
        return result;
    }

    /**
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(contentType);
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and validate the state.
        validateState();
    }

    protected void validateState() {
        Objects.requireNonNull(contentType, "Content type is required");
    }

}
