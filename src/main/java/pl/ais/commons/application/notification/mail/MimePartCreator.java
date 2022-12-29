package pl.ais.commons.application.notification.mail;

import pl.ais.commons.application.notification.NotificationException;
import pl.ais.commons.application.notification.component.Attachment;
import pl.ais.commons.application.notification.component.MultipartAlternative;
import pl.ais.commons.application.notification.component.MultipartMixed;
import pl.ais.commons.application.notification.component.NotificationComponent;
import pl.ais.commons.application.notification.component.NotificationComponentVisitor;
import pl.ais.commons.application.notification.component.Text;

import jakarta.activation.DataHandler;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimePart;

/**
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@NotThreadSafe
class MimePartCreator implements NotificationComponentVisitor {

    private final MimePart container;

    MimePartCreator(@Nonnull final MimePart container) {
        this.container = container;
    }

    private void processMultipart(final String subType, final Iterable<NotificationComponent> multipart) {
        final MimeMultipart content = new MimeMultipart(subType);
        final NotificationComponentVisitor creator = new MimeMultipartCreator(content);
        multipart.forEach(nc -> nc.accept(creator));
        try {
            container.setContent(content);
        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attachment attachment) {
        throw new IllegalArgumentException("Attachment cannot be primary part of notification.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final MultipartAlternative multipart) {
        processMultipart("alternative", multipart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Text text) {
        try {
            container.setDataHandler(new DataHandler(text));
        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final MultipartMixed multipart) {
        processMultipart("mixed", multipart);
    }

}
