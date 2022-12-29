package pl.ais.commons.application.notification.mail;

import pl.ais.commons.application.notification.NotificationException;
import pl.ais.commons.application.notification.component.Attachment;
import pl.ais.commons.application.notification.component.MultipartAlternative;
import pl.ais.commons.application.notification.component.MultipartMixed;
import pl.ais.commons.application.notification.component.NotificationComponentVisitor;
import pl.ais.commons.application.notification.component.Text;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;

/**
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
class MimeMultipartCreator implements NotificationComponentVisitor {

    private final MimeMultipart container;

    MimeMultipartCreator(final MimeMultipart multipart) {
        container = multipart;
    }

    private void processDataSource(final DataSource dataSource) {
        final MimeBodyPart bodyPart = new MimeBodyPart();
        try {
            bodyPart.setDataHandler(new DataHandler(dataSource));
            container.addBodyPart(bodyPart);
        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attachment attachment) {
        processDataSource(attachment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final MultipartAlternative multipart) {
        final MimeBodyPart part = new MimeBodyPart();
        multipart.accept(new MimePartCreator(part));
        try {
            container.addBodyPart(part);
        } catch (final MessagingException exception) {
            throw new NotificationException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final MultipartMixed multipart) {
        throw new IllegalArgumentException("Multipart/mixed should be primary part of notification.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Text text) {
        processDataSource(text);
    }

}
