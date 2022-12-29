package pl.ais.commons.application.notification.mail;

import pl.ais.commons.application.notification.NotificationException;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.event.TransportEvent;
import jakarta.mail.event.TransportListener;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.stream.Collectors.joining;

/**
 * {@link TransportListener} implementation notifying given delegate about the message delivery.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.3
 */
public class DelegatingTransportListener implements TransportListener {

    private final Consumer<String> delegate;

    DelegatingTransportListener(final Consumer<String> delegate) {
        this.delegate = delegate;
    }

    private static Optional<String> getMessageId(final Message message) throws MessagingException {
        return Optional.ofNullable(message.getHeader("Message-Id"))
                       .map(anArray -> anArray[0]);
    }

    /**
     * Creates and returns new instance of {@link DelegatingTransportListener} listener.
     *
     * @param delegate delegate to be notified about message delivery
     * @return newly create {@link DelegatingTransportListener} instance using given {@code delegate}
     */
    public static TransportListener onMessageDelivery(final Consumer<String> delegate) {
        return new DelegatingTransportListener(delegate);
    }

    private static Optional<String> toString(final Address[] addresses) {
        return Optional.ofNullable(addresses)
                       .map(Arrays::stream)
                       .map(address -> address.map(Address::toString)
                                              .collect(joining(", ")));
    }

    @Override
    public void messageDelivered(final TransportEvent event) {
        final Optional<String> recipients = toString(event.getValidSentAddresses());
        final Message message = event.getMessage();
        try {
            final Optional<String> messageId = getMessageId(message);
            delegate.accept(
                String.format("Message \"%s\" [%s] delivered successfully to: %s", message.getSubject(),
                    messageId.orElse("--"), recipients.orElse("--")));
        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

    @Override
    public void messageNotDelivered(final TransportEvent event) {
        final Optional<String> validUnsent = toString(event.getValidUnsentAddresses());
        final Optional<String> invalid = toString(event.getInvalidAddresses());
        final Message message = event.getMessage();
        try {
            final Optional<String> messageId = getMessageId(message);
            delegate.accept(
                String.format("Unable to deliver message: \"%s\" [%s] to: %s, invalid addresses: %s", message.getSubject(),
                    messageId.orElse("--"), validUnsent.orElse("--"), invalid.orElse("--")));
        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

    @Override
    public void messagePartiallyDelivered(final TransportEvent event) {
        final Optional<String> validSent = toString(event.getValidSentAddresses());
        final Optional<String> validUnsent = toString(event.getValidUnsentAddresses());
        final Optional<String> invalid = toString(event.getInvalidAddresses());
        final Message message = event.getMessage();
        try {
            final Optional<String> messageId = getMessageId(message);
            delegate.accept(
                String.format("Message \"%s\" [%s] delivered partially, send to: %s, unable to send to: %s, invalid addresses: %s",
                    message.getSubject(), messageId.orElse("Unknown Id"), validSent.orElse("--"),
                    validUnsent.orElse("--"), invalid.orElse("--")));
        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

}
