package pl.ais.commons.application.notification.component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * Text.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@Immutable
public final class Text extends TypedData {

    private static final String DEFAULT_CHARSET = "US-ASCII";

    private static final long serialVersionUID = -577763928597281842L;

    /**
     * Name of the charset used for encoding the content.
     *
     * @serial
     * @since 1.2.1
     */
    private final String charsetName;

    /**
     * The content.
     *
     * @serial
     * @since 1.2.1
     */
    private final String content;

    private Text(@Nonnull final String contentType, @Nonnull final String content, @Nullable final String charsetName) {
        super(String.format("%s; charset=%s", contentType, charsetName));

        this.charsetName = charsetName;
        this.content = content;
    }

    /**
     * Creates and returns new {@link Text} instance holding given HTML content.
     *
     * <p><strong>Note:</strong> this method assumes that given content is encoded using '{@value #DEFAULT_CHARSET}'
     * encoding</p>
     *
     * @param content HTML content to be enclosed within created instance of {@link Text}
     * @return newly created {@link Text} instance holding given HTML content
     */
    public static Text html(@Nonnull final String content) {
        return new Text("text/html", content, DEFAULT_CHARSET);
    }

    /**
     * Creates and returns new {@link Text} instance holding given HTML content.
     *
     * @param content     HTML content to be enclosed within created instance of {@link Text}
     * @param charsetName name of the charset used for content encoding
     * @return newly created {@link Text} instance holding given HTML content
     */
    public static Text html(@Nonnull final String content, @Nonnull final String charsetName) {
        return new Text("text/html", content, charsetName);
    }

    /**
     * Creates and returns new {@link Text} instance holding given plain text content.
     *
     * <p><strong>Note:</strong> this method assumes that given content is encoded using '{@value #DEFAULT_CHARSET}'
     * encoding</p>
     *
     * @param content plain text content to be enclosed within created instance of {@link Text}
     * @return newly created {@link Text} instance holding given plain text content
     */
    public static Text text(final String content) {
        return new Text("text/plain", content, DEFAULT_CHARSET);
    }

    /**
     * Creates and returns new {@link Text} instance holding given plain text content.
     *
     * @param content     plain text content to be enclosed within created instance of {@link Text}
     * @param charsetName name of the charset used for content encoding
     * @return newly created {@link Text} instance holding given plain text content
     */
    public static Text text(@Nonnull final String content, @Nonnull final String charsetName) {
        return new Text("text/html", content, charsetName);
    }

    /**
     * Accepts given visitor, and let him perform some tasks on this instance of Text.
     *
     * @param visitor the visitor to be accepted
     */
    public void accept(final NotificationComponentVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content.getBytes(charsetName));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return null;
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and validate the state.
        validateState();
    }

    @Override
    protected void validateState() {
        super.validateState();

        Objects.requireNonNull(content, "Content is required.");
        Objects.requireNonNull(charsetName, "Name of the charset is required.");
    }

    /**
     * Creates and returns <em>multipart/alternative</em>, holding this text and provided alternative.
     *
     * @param alternative alternative content
     * @return newly created <em>multipart/alternative</em>, holding this text and provided alternative
     */
    public MultipartAlternative withAlternative(final Text alternative) {
        return new MultipartAlternative(this, alternative);
    }

    /**
     * Creates and returns <em>multipart/mixed</em>, holding this text and provided attachment.
     *
     * @param attachment the attachment to be mixed with this text
     * @return newly create <em>multipart/mixed</em>, holding this text and provided attachment
     */
    public MultipartMixed withAttachment(@Nonnull final Attachment attachment) {
        return new MultipartMixed(this, attachment);
    }

}
