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

    private static final long serialVersionUID = -2511649532981437114L;

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
     * @return the content type
     */
    public String getContentType() {
        return contentType;
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
