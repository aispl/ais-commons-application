package pl.ais.commons.application.notification.component;

/**
 * Defines the API contract for notification component visitor.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public interface NotificationComponentVisitor {

    void visit(Attachment attachment);

    void visit(MultipartAlternative multipart);

    void visit(MultipartMixed multipart);

    void visit(Text text);

}
