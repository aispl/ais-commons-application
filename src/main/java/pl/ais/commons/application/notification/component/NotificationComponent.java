package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;

/**
 * Defines the API contract for notification component.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public interface NotificationComponent {

    /**
     * Accepts given visitor, and let him perform some tasks on this notification component.
     *
     * @param visitor the visitor to be accepted
     */
    void accept(@Nonnull NotificationComponentVisitor visitor);

}
