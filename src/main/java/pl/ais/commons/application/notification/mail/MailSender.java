package pl.ais.commons.application.notification.mail;

import jakarta.mail.event.TransportListener;
import pl.ais.commons.application.notification.AddressedNotification;

import java.util.function.BiConsumer;

/**
 * Defines the API contract for Mail Sender.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public interface MailSender extends BiConsumer<AddressedNotification, TransportListener[]> {

    /**
     * Accepts given notification for the delivery.
     *
     * @param notification a notification to be delivered
     * @param listeners    listeners watching the delivery process
     */
    @Override
    void accept(AddressedNotification notification, TransportListener... listeners);

}
