package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;

/**
 * Multipart.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public abstract class Multipart extends TypedContent implements Iterable<NotificationComponent> {

    private static final long serialVersionUID = -2045795397379622793L;

    /**
     * Components of this multipart.
     *
     * @serial
     * @since 1.2.1
     */
    private final List<NotificationComponent> components;

    /**
     * Constructs new instance.
     *
     * @param subType <em>multipart</em> subtype specific for the instance (ex. <em>alternative</em>, <em>mixed</em>)
     * @param first   first multipart component
     * @param second  second multipart component
     * @param rest    remaining multipart components
     */
    Multipart(@Nonnull final String subType, final NotificationComponent first,
              final NotificationComponent second, final NotificationComponent... rest) {
        super(String.format("multipart/%s", subType));

        final List<NotificationComponent> parts = new ArrayList<>();
        parts.add(first);
        parts.add(second);
        parts.addAll(Arrays.asList(rest));
        components = Collections.unmodifiableList(parts);
    }

    /**
     * @return unmodifiable view of the multipart components
     */
    public List<NotificationComponent> getComponents() {
        return components;
    }

    /**
     * @return iterator over all components of this multipart
     */
    @Override
    public Iterator<NotificationComponent> iterator() {
        return components.iterator();
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and validate the state.
        validateState();
    }

    @Override
    public String toString() {
        return String.format("%s: [%s]", getContentType(),
            StreamSupport.stream(spliterator(), false)
                         .map(NotificationComponent::toString)
                         .collect(joining(", ")));
    }

    @Override
    protected void validateState() {
        super.validateState();
        Objects.requireNonNull(components, "Multipart components are required");
    }

}
