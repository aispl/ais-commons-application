package pl.ais.commons.application.util.jquery.datatables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import pl.ais.commons.query.AbstractSelection;
import pl.ais.commons.query.SearchResults;
import pl.ais.commons.query.SearchResultsProvider;
import pl.ais.commons.query.Selection;

/**
 * Utility class for building jQuery DataTables input data.
 *
 * @param <E> determines the type of each search result
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class DataTablesBuilder<E extends Serializable> {

    private transient final SearchResultsConverter<E> converter;

    private transient SearchResults<E> results;

    private transient Selection selection;

    /**
     * Constructs new instance.
     *
     * @param converter
     *            the search results converter which should be used while preparing the data
     */
    public DataTablesBuilder(final SearchResultsConverter<E> converter) {
        this.converter = converter;
    }

    /**
     * Re-creates the selection of data visible on UI using HTTP request parameters.
     *
     * @param request
     *            currently serviced HTTP request
     * @param sortingColsNo
     *            determines the number of columns used in sorting the data
     * @param orderings
     *            orderings which are available for the search results
     * @return the builder itself
     */
    public <R extends Serializable> DataTablesBuilder<E> andOrderings(
        final HttpServletRequest request, final int sortingColsNo, final R[]... orderings) {
        for (int i = 0; i < sortingColsNo; i++) {
            int index = 2 * Integer.parseInt(request.getParameter(JQueryDataTables.SORT_COL_PREFIX + i));
            if (JQueryDataTables.DESC.equalsIgnoreCase(request.getParameter(JQueryDataTables.SORT_DIR + i))) {
                index++;
            }
            selection = selection.withOrderings(orderings[index]);
        }
        return this;
    }

    /**
     * Creates a Map holding all informations required by jQuery DataTables for viewing the data.
     *
     * @param request currently serviced HTTP request
     * @return a Map holding all informations required by jQuery DataTables for viewing the data
     */
    @SuppressWarnings("boxing")
    public Map<String, Object> buildInResponseTo(final HttpServletRequest request) {
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(JQueryDataTables.DATA, converter.convertToData(request, results));
        result.put(JQueryDataTables.TOTAL_NO, results.getTotalRecords());
        result.put(JQueryDataTables.FILTERED_NO, results.getTotalRecords());
        return result;
    }

    /**
     * Remember the results provided by given results provider for further usage.
     *
     * @param resultsProvider the results provider
     * @return the builder itself
     */
    public DataTablesBuilder<E> forResultsProvidedBy(final SearchResultsProvider<E> resultsProvider) {
        this.results = resultsProvider.provideForSelection(selection);
        return this;
    }

    /**
     * Re-creates the {@link AbstractSelection} using default ordering applier and provided parameters.
     *
     * @param selection selection which should be used
     * @return the builder itself
     */
    public DataTablesBuilder<E> withSelection(@SuppressWarnings("hiding") final Selection selection) {
        this.selection = selection;
        return this;
    }

}
