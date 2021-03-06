package pl.ais.commons.application.util.http;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.ais.commons.application.util.http.ResponseEntities.textHtml;

/**
 * @author Warlock, AIS.PL
 * @since 1.3.2
 */
@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class ResponseEntitiesExpectations {

    @Test
    public void shouldReturnAllErrorsForBindingResult() {

        // Given binding result with a global error, and a field error.  
        final MrBean aBean = new MrBean();
        final BindingResult bindingResult = new BeanPropertyBindingResult(aBean, "aBean");
        bindingResult.reject("disallowed");
        bindingResult.rejectValue("nickname", "required");

        // When we create the response for this binding result. 
        final Optional<ResponseEntity<?>> optionalResponse = ResponseEntities.allErrors(bindingResult);

        // Then it will have 'Bad Request' status, and provide all errors as response body.
        assertTrue("Response should be present", optionalResponse.isPresent());

        final ResponseEntity<?> response = optionalResponse.get();
        assertEquals("Response status code should be 'Bad Request' (400)",
            HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Response body should enclose all validation errors",
            bindingResult.getAllErrors(), response.getBody());
    }

    @Test
    public void shouldReturnEmptyResponseWithTextHtmlContentTypeForOkMethod() {
        final ResponseEntity<?> response = ResponseEntities.ok();

        assertEquals("Response status code should be 'OK' (200)",
            HttpStatus.OK, response.getStatusCode());
        assertEquals("Response content type should be 'text/html', with charset UTF-8",
            textHtml(), response.getHeaders().getContentType());
    }

    @Test
    public void shouldReturnProvidedValueForCreatedMethod() {

        // Given some value
        final Long entityId = Long.valueOf(1);

        // When we use 'created' method
        final ResponseEntity<Long> response = ResponseEntities.created(entityId);

        // Then it will provide a response entity with 'Created' status and given value as response body.
        assertEquals("Response status code should be 'Created' (201)",
            HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Response body should enclose entity ID", entityId, response.getBody());
    }

    private static class MrBean {

        private String nickname;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(final String nickname) {
            this.nickname = nickname;
        }

    }
}
