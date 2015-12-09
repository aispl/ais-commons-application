package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * Attachment.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@Immutable
public final class Attachment extends TypedData {

    private static final long serialVersionUID = 3953987123941732863L;

    /**
     * Content of the attachment.
     *
     * @serial
     * @since 1.2.1
     */
    private final byte[] content;

    /**
     * Name of the attachment.
     *
     * @serial
     * @since 1.2.1
     */
    private final String name;

    // TODO: consider the possible ways of attachments creating
    Attachment(@Nonnull final String contentType, @Nonnull final String name, @Nonnull final byte[] content) {
        super(contentType);

        this.content = content.clone();
        this.name = name;
    }

    /**
     * Accepts given visitor, and let him perform some tasks on this instance of Attachment.
     *
     * @param visitor the visitor to be accepted
     */
    @Override
    public void accept(@Nonnull final NotificationComponentVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and validate the state.
        validateState();
    }

    @Override
    protected void validateState() {
        super.validateState();

        Objects.requireNonNull(name, "Attachment name is required.");
        Objects.requireNonNull(content, "Attachment content is required.");
    }

}
