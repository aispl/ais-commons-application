package pl.ais.commons.application.notification.component;

import javax.activation.DataSource;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Typed data.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
abstract class TypedData extends TypedContent implements DataSource {

    private static final long serialVersionUID = 1673609156463676795L;

    protected TypedData(@Nonnull final String contentType) {
        super(contentType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

}
