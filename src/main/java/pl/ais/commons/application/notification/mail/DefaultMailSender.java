package pl.ais.commons.application.notification.mail;

import pl.ais.commons.application.notification.AddressType;
import pl.ais.commons.application.notification.AddressedNotification;
import pl.ais.commons.application.notification.NotificationException;
import pl.ais.commons.application.notification.component.Subject;

import jakarta.mail.Address;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.event.TransportListener;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Default {@link MailSender} implementation.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public final class DefaultMailSender implements MailSender {

    private final Session session;

    public DefaultMailSender(final Session session) {
        this.session = session;
    }

    private static RecipientType mapType(final AddressType type) {
        final RecipientType result;
        switch (type) {
            case BCC:
                result = RecipientType.BCC;
                break;

            case CC:
                result = RecipientType.CC;
                break;

            default:
                result = RecipientType.TO;
                break;
        }
        return result;
    }

    @Override
    public void accept(final AddressedNotification notification, final TransportListener... listeners) {

        final MimeMessage message = new MimeMessage(session);
        try {
            message.setSentDate(new Date());

            final String sender = notification.getSender();
            if ((null == sender) || sender.isEmpty()) {
                message.setFrom();
            } else {
                final InternetAddress senderAddress = new InternetAddress(sender);
                message.setFrom(senderAddress);
                message.setReplyTo(new Address[] {senderAddress});
            }

            EnumSet.allOf(AddressType.class)
                   .forEach(addressType -> {
                       final List<Address> addresses = notification.getRecipients(addressType)
                                                                   .flatMap(this::parseAddress)
                                                                   .collect(toList());
                       try {
                           message.setRecipients(mapType(addressType), addresses.toArray(new Address[addresses.size()]));
                       } catch (final MessagingException exception) {
                           throw new NotificationException(exception);
                       }
                   });

            final Subject subject = notification.getSubject();
            message.setSubject(subject.toString(), subject.getCharsetName());

            notification.apply(new MimePartCreator(message));

            message.saveChanges();

            final Transport transport = session.getTransport();
            Arrays.stream(listeners)
                  .forEachOrdered(transport::addTransportListener);
            try {
                transport.connect();
                transport.sendMessage(message, message.getAllRecipients());
            } finally {
                transport.close();
            }

        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

    private Stream<Address> parseAddress(final String recipient) {
        try {
            return Arrays.stream(InternetAddress.parse(recipient));
        } catch (final AddressException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

}
