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
public final class Notification implements Serializable {

    private static final long serialVersionUID = 5207147563732093670L;

    private final NotificationComponent content;

    private final Subject subject;

    /**
     * Creates new instance.
     *
     * @param builder the builder providing all information required for creating the notification instance
     */
    Notification(final Builder builder) {
        super();

        content = builder.content;
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
     * @return notification content
     */
    public NotificationComponent getContent() {
        return content;
    }

    /**
     * @return notification subject
     */
    public Subject getSubject() {
        return subject;
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and validate the state.
        validateState();
    }

    protected void validateState() {
        Objects.requireNonNull(content, "Notification content is required.");
        Objects.requireNonNull(subject, "Notification subject is required.");
    }

    /**
     * Builder suitable for creating Notification instances.
     */
    public static class Builder implements Supplier<Notification> {

        private NotificationComponent content;

        private Subject subject;

        /**
         * Constructs new instance.
         */
        private Builder() {
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

    }

}
