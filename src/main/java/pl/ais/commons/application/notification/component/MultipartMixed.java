package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Multipart - Mixed.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@Immutable
public final class MultipartMixed extends Multipart {

    private static final long serialVersionUID = 7770328513771016365L;

    /**
     * Constructs new instance.
     *
     * @param first  first multipart component
     * @param second second multipart component
     * @param rest   remaining multipart components
     */
    public MultipartMixed(final NotificationComponent first, final NotificationComponent second, final NotificationComponent... rest) {
        super("multipart/mixed", first, second, rest);
    }

    /**
     * Accepts given visitor, and let him perform some tasks on this instance of Multipart Mixed.
     *
     * @param visitor the visitor to be accepted
     */
    @Override
    public void accept(@Nonnull final NotificationComponentVisitor visitor) {
        visitor.visit(this);
    }

}
