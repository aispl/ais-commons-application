package pl.ais.commons.application.notification;

import pl.ais.commons.application.notification.component.NotificationComponent;
import pl.ais.commons.application.notification.component.NotificationComponentVisitor;
import pl.ais.commons.application.notification.component.Subject;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Notification.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@Immutable
@SuppressWarnings("PMD.TooManyMethods")
public final class Notification implements Serializable {

    private static final long serialVersionUID = 7012801705834810129L;

    private final NotificationComponent content;

    private final String sender;

    private final Subject subject;

    /**
     * Creates new instance.
     *
     * @param builder the builder providing all information required for creating the notification instance
     */
    Notification(final Builder builder) {
        super();

        content = builder.content;
        sender = builder.sender;
        subject = builder.subject;
    }

    /**
     * @return newly created instance of builder suitable for creating the notification
     */
    public static Builder aNotification() {
        return new Builder();
    }

    /**
     * @return newly created instance of builder suitable for creating addressed notification
     */
    public AddressedNotification.Builder addressed() {
        return AddressedNotification.address(this);
    }

    /**
     * Applies given visitor to this notification content.
     *
     * @param visitor the visitor to be applied
     */
    public void apply(@Nonnull final NotificationComponentVisitor visitor) {
        content.accept(visitor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (object instanceof Notification)) {
            final Notification other = (Notification) object;
            result = Objects.equals(subject, other.subject) && Objects.equals(content, other.content);
        }
        return result;
    }

    /**
     * @return notification content
     */
    public NotificationComponent getContent() {
        return content;
    }

    /**
     * @return notification sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return notification subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(subject, content);
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
    @Override
    public String toString() {
        return String.format("%s [%s]", subject, content);
    }

    private void validateState() {
        Objects.requireNonNull(content, "Notification content is required.");
        Objects.requireNonNull(subject, "Notification subject is required.");
    }

    /**
     * Builder suitable for creating Notification instances.
     */
    public static class Builder implements Supplier<Notification> {

        private NotificationComponent content;

        private String sender;

        private Subject subject;

        /**
         * Constructs new instance.
         */
        Builder() {
            super();
        }

        /**
         * @return newly created instance of builder suitable for creating addressed notification
         */
        public AddressedNotification.Builder addressed() {
            return AddressedNotification.address(get());
        }

        /**
         * @return newly created Notification instance
         */
        @Override
        public Notification get() {
            return new Notification(this);
        }

        public Builder sentBy(final String sender) {
            this.sender = sender;
            return this;
        }

        /**
         * @param content notification content
         * @return the builder itself, for method invocation chaining
         */
        public Builder withContent(final NotificationComponent content) {
            this.content = content;
            return this;
        }

        /**
         * @param subject     the subject
         * @param charsetName name of the charset used for encoding the subject
         * @return the builder itself, for method invocation chaining
         */
        public Builder withSubject(final String subject, final String charsetName) {
            this.subject = Subject.subject(subject, charsetName);
            return this;
        }

        /**
         * @param subject the subject
         * @return the builder itself, for method invocation chaining
         */
        public Builder withSubject(final Subject subject) {
            this.subject = subject;
            return this;
        }

    }

}
