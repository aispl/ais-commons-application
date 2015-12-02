package pl.ais.commons.application.notification.mail;

import pl.ais.commons.application.notification.AddressType;
import pl.ais.commons.application.notification.AddressedNotification;
import pl.ais.commons.application.notification.NotificationException;
import pl.ais.commons.application.notification.component.Subject;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public final class MailSender {

    private final Session session;

    public MailSender(final Session session) {
        this.session = session;
    }

    public void accept(final AddressedNotification notification, final TransportListener... listeners) {

        final MimeMessage message = new MimeMessage(session);
        try {
            message.setSentDate(new Date());

            // TODO: currently we use session property 'mail.from' to initialize it
            message.setFrom();

            EnumSet.allOf(AddressType.class)
                   .forEach(addressType -> {
                       final List<Address> addresses = notification.getRecipients(addressType)
                                                                   .flatMap(recipient -> parseAddress(recipient))
                                                                   .collect(Collectors.toList());
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
                  .forEachOrdered(listener -> transport.addTransportListener(listener));
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

    private RecipientType mapType(final AddressType type) {
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

    private Stream<Address> parseAddress(final String recipient) {
        try {
            return Arrays.stream(InternetAddress.parse(recipient));
        } catch (final AddressException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

}
