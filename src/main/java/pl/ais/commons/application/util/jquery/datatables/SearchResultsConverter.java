package pl.ais.commons.application.util.jquery.datatables;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import pl.ais.commons.query.SearchResults;

/**
 * Defines the API contract for converting {@link SearchResults} into representation needed by jQuery DataTables.
 *
 * @param <E> determines the type of search results elements
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface SearchResultsConverter<E extends Serializable> {

    /**
     * Converts the search results into the representation needed by jQuery DataTables.
     *
     * @param request currently serviced HTTP request
     * @param searchResults the search results to convert
     * @return search results representation needed by jQuery DataTables
     */
    List<Object[]> convertToData(HttpServletRequest request, SearchResults<E> searchResults);

}
