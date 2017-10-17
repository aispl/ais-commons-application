package pl.ais.commons.application.util.http;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.annotation.concurrent.Immutable;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

/**
 * Utility class for preparing response entities.
 *
 * @author Warlock, AIS.PL
 * @since 1.3.2
 */
@Immutable
public final class ResponseEntities {

    private ResponseEntities() {
        throw new AssertionError("This class shouldn't be instantiated.");
    }

    /**
     * @param bindingResult binding result to be processed
     * @return an {@code Optional} holding 'Bad Request' (400) response entity enclosing all validation errors,
     * or {@link Optional#empty()} if provided {@code bindingResult} has no validation errors
     */
    public static Optional<ResponseEntity<?>> allErrors(final BindingResult bindingResult) {
        return bindingResult.hasErrors() ?
            Optional.of(badRequest(bindingResult.getAllErrors())) : Optional.empty();
    }

    /**
     * @param entity entity to be returned in response
     * @param <T>    defines the type of entity returned in response
     * @return 'Bad Request' (400) response enclosing given {@code entity}
     */
    public static <T> ResponseEntity<T> badRequest(final T entity) {
        return new ResponseEntity<>(entity, BAD_REQUEST);
    }

    /**
     * @param <T> type of entity returned, if there was any, in the response
     * @return empty 'Bad Request' (400) response
     */
    public static <T> ResponseEntity<T> badRequest() {
        return new ResponseEntity<>(BAD_REQUEST);
    }

    /**
     * @param entity entity to be returned in response
     * @param <T>    defines the type of entity returned in response
     * @return 'Created' (201) response enclosing given {@code entity}
     */
    public static <T> ResponseEntity<T> created(final T entity) {
        return new ResponseEntity<>(entity, CREATED);
    }

    /**
     * @param <T> type of entity returned, if there was any, in the response
     * @return empty 'Gone' (410) response
     */
    public static <T> ResponseEntity<T> gone() {
        return new ResponseEntity<>(GONE);
    }

    /**
     * @param <T> type of entity returned, if there was any, in the response
     * @return empty 'No content' (204) response
     */
    public static <T> ResponseEntity<T> noContent() {
        return new ResponseEntity<>(NO_CONTENT);
    }

    /**
     * @param <T> type of entity returned, if there was any, in the response
     * @return empty 'Not Found' (404) response, with content type <em>text/html</em>, and charset encoding <em>UTF-8</em>
     */
    public static <T> ResponseEntity<T> notFound() {
        return notFound(textHtml());
    }

    /**
     * @param <T> type of entity returned, if there was any, in the response
     * @return empty 'Not Found' (404) response, with content type and charset encoding defined by given {@code mediaType}
     */
    public static <T> ResponseEntity<T> notFound(final MediaType mediaType) {
        return ResponseEntity.status(NOT_FOUND)
                             .contentType(mediaType)
                             .build();
    }

    /**
     * @param <T> type of entity returned, if there was any, in the response
     * @return empty 'OK' (200) response, with content type <em>text/html</em>, and charset encoding <em>UTF-8</em>
     */
    public static <T> ResponseEntity<T> ok() {
        return ok(textHtml());
    }

    /**
     * @param <T> type of entity returned, if there was any, in the response
     * @return empty 'OK' (200) response, with content type and charset encoding defined by given {@code mediaType}
     */
    public static <T> ResponseEntity<T> ok(final MediaType mediaType) {
        return ResponseEntity.status(OK)
                             .contentType(mediaType)
                             .build();
    }

    /**
     * @param entity entity to be returned in response
     * @param <T>    defines the type of entity returned in response
     * @return 'OK' (200) response enclosing given {@code entity}
     */
    public static <T> ResponseEntity<T> ok(final T entity) {
        return new ResponseEntity<>(entity, OK);
    }

    /**
     * @param entity    entity to be returned in response
     * @param mediaType response content type (and charset encoding)
     * @param <T>       defines the type of entity returned in response
     * @return 'OK' (200) response, enclosing given entity, with content type and charset encoding defined
     * by given {@code mediaType}
     */
    public static <T> ResponseEntity<T> ok(final T entity, final MediaType mediaType) {
        return ResponseEntity.status(OK)
                             .contentType(mediaType)
                             .body(entity);
    }

    /**
     * @param content   response content
     * @param mediaType response content type
     * @return 'OK' (200) response, enclosing given content, with content type defined by given {@code mediaType}
     */
    public static ResponseEntity<byte[]> ok(final byte[] content, final MediaType mediaType) {
        return ok(content, mediaType, CacheControl.empty());
    }

    /**
     * @param content      response content
     * @param mediaType    response content type
     * @param cacheControl response caching directives
     * @return 'OK' (200) response, enclosing given content, with content type defined by given {@code mediaType},
     * and caching directives defined by {@code cacheControl}
     */
    public static ResponseEntity<byte[]> ok(final byte[] content, final MediaType mediaType,
                                            final CacheControl cacheControl) {
        return ResponseEntity.status(OK)
                             .contentLength(content.length)
                             .contentType(mediaType)
                             .cacheControl(cacheControl)
                             .body(content);
    }

    static MediaType textHtml() {
        return new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
    }

}
