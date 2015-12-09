package pl.ais.commons.application.notification;

import pl.ais.commons.application.notification.component.NotificationComponent;
import pl.ais.commons.application.notification.component.NotificationComponentVisitor;
import pl.ais.commons.application.notification.component.Subject;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static pl.ais.commons.application.util.ArrayUtils.streamOf;

/**
 * Addressed notification.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@Immutable
@SuppressWarnings("PMD.TooManyMethods")
public final class AddressedNotification implements Serializable {

    private static final long serialVersionUID = 6490643252030586946L;

    private final Notification notification;

    private final Map<String, AddressType> recipients;

    /**
     * Creates new instance.
     *
     * @param builder the builder providing all information required for creating the notification instance
     */
    AddressedNotification(final Builder builder) {
        super();

        notification = builder.notification;
        recipients = new LinkedHashMap<>(builder.recipients);
    }

    /**
     * Creates and returns the builder suitable for creating Addressed Notification instances.
     *
     * @param notification notification to be addressed
     * @return newly created instance of the builder suitable for creating Addressed Notification instances
     */
    public static Builder address(final Notification notification) {
        return new Builder(notification);
    }

    /**
     * Applies given visitor to this notification content.
     *
     * @param visitor the visitor to be applied
     */
    public void apply(@Nonnull final NotificationComponentVisitor visitor) {
        notification.apply(visitor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (object instanceof AddressedNotification)) {
            final AddressedNotification other = (AddressedNotification) object;
            result = Objects.equals(notification, other.notification) && Objects.equals(recipients, other.recipients);
        }
        return result;
    }

    /**
     * @return notification content
     */
    public NotificationComponent getContent() {
        return notification.getContent();
    }

    /**
     * @return stream of the recipients of the desired type
     */
    public Stream<String> getRecipients(@Nonnull final AddressType desiredType) {
        return recipients.entrySet()
                         .stream()
                         .filter(candidate -> desiredType == candidate.getValue())
                         .map(Entry::getKey);
    }

    /**
     * @return notification subject
     */
    public Subject getSubject() {
        return notification.getSubject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(notification, recipients);
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
        return String.format("%s addressed to: %s", notification,
            recipients.entrySet().stream()
                      .map(entry -> (AddressType.PRIMARY == entry.getValue()) ? entry.getKey() :
                          String.format("%s (%s)", entry.getKey(), entry.getValue()))
                      .collect(joining(", ")));
    }

    private void validateState() {
        Objects.requireNonNull(notification, "Notification is required.");
        Objects.requireNonNull(recipients, "Recipients are required.");
    }

    /**
     * Builder suitable for creating Addressed Notification instances.
     */
    public static class Builder implements Supplier<AddressedNotification> {

        private final Notification notification;

        private final Map<String, AddressType> recipients;

        Builder(final Notification notification) {
            super();

            this.notification = notification;
            recipients = new LinkedHashMap<>();
        }

        /**
         * Adds recipients of type {@link AddressType#BCC}.
         *
         * @param first first recipient to be added
         * @param rest  remaining recipients to be added
         * @return the builder itself, for method invocation chaining
         */
        public Builder bcc(final String first, final String... rest) {
            return withRecipients(AddressType.BCC, first, rest);
        }

        /**
         * Adds recipients of type {@link AddressType#CC}.
         *
         * @param first first recipient to be added
         * @param rest  remaining recipients to be added
         * @return the builder itself, for method invocation chaining
         */
        public Builder cc(final String first, final String... rest) {
            return withRecipients(AddressType.CC, first, rest);
        }

        /**
         * @return newly created Addressed Notification
         */
        @Override
        public AddressedNotification get() {
            return new AddressedNotification(this);
        }

        private <T> BinaryOperator<T> throwingMerger() {
            return (u, v) -> {
                throw new IllegalStateException(String.format("Duplicate value found: %s", u));
            };
        }

        /**
         * Adds recipients of type {@link AddressType#PRIMARY}.
         *
         * @param first first recipient to be added
         * @param rest  remaining recipients to be added
         * @return the builder itself, for method invocation chaining
         */
        public Builder to(final String first, final String... rest) {
            return withRecipients(AddressType.PRIMARY, first, rest);
        }

        private Builder withRecipients(final AddressType type, final String first, final String... rest) {
            recipients.putAll(
                streamOf(String.class, first, rest)
                    .collect(toMap(identity(), __ -> type, throwingMerger(), LinkedHashMap::new)));
            return this;
        }

    }

}
