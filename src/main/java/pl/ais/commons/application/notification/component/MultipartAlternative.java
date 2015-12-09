package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Multipart - Alternative.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@Immutable
public final class MultipartAlternative extends Multipart {

    private static final long serialVersionUID = -8666031612758488468L;

    /**
     * Constructs new instance.
     *
     * @param first  first multipart component
     * @param second second multipart component
     * @param rest   remaining multipart components
     */
    public MultipartAlternative(final NotificationComponent first, final NotificationComponent second, final NotificationComponent... rest) {
        super("alternative", first, second, rest);
    }

    /**
     * Accepts given visitor, and let him perform some tasks on this instance of Multipart Alternative.
     *
     * @param visitor the visitor to be accepted
     */
    @Override
    public void accept(@Nonnull final NotificationComponentVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Combines this <em>multipart/alternative</em> with the provided attachment.
     *
     * @param attachment the attachment to be combined with this <em>multipart/alternative</em>
     * @return newly created <em>multipart/mixed</em> holding this <em>multipart/alternative</em> and given attachment
     */
    public MultipartMixed withAttachment(final Attachment attachment) {
        return new MultipartMixed(this, attachment);
    }

}
