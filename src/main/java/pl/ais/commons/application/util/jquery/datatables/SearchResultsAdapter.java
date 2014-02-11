package pl.ais.commons.application.util.jquery.datatables;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.Immutable;
import javax.servlet.http.HttpServletRequest;

import pl.ais.commons.query.SearchResults;
import pl.ais.commons.query.SearchResultsProvider;
import pl.ais.commons.query.Selection;
import pl.ais.commons.query.SelectionFactory;
import pl.ais.commons.query.Selections;

import com.google.common.base.Function;

/**
 * Adapts ...
 *
 * @param <E> the type of each search result
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
final class SearchResultsAdapter<E extends Serializable> implements TabularDataProvider {

    /**
     * Defines the API contract for converting {@link SearchResults} into tabular data.
     *
     * @param <E> the type of each search result
     */
    interface Converter<E extends Serializable> extends Function<SearchResults<E>, Object[][]> {

        // Empty by design ...

    }

    /**
     * @param <R>
     * @param <S>
     */
    @Immutable
    private final class OrderedSearchResultsAdapter<R extends Serializable, S extends Selection<R>> implements
        TabularDataProvider {

        private final R[][] orderings;

        private final SelectionFactory<R, S> selectionFactory;

        /**
         * Constructs new instance.
         *
         * @param selectionFactory selection factory
         * @param orderings data orderings
         */
        @SafeVarargs
        protected OrderedSearchResultsAdapter(final SelectionFactory<R, S> selectionFactory, final R[]... orderings) {
            this.orderings = Arrays.copyOf(orderings, orderings.length);
            this.selectionFactory = selectionFactory;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<String, Object> inResponseTo(final HttpServletRequest request) {
            Selection<R> selection = createSelection(request, selectionFactory);
            final int sortingColsNo = getIntParameter(request, JQueryDataTables.SORT_COLS_NO, 0);
            for (int i = 0; i < sortingColsNo; i++) {
                int index = 2 * Integer.parseInt(request.getParameter(JQueryDataTables.SORT_COL_PREFIX + i));
                if (JQueryDataTables.DESC.equalsIgnoreCase(request.getParameter(JQueryDataTables.SORT_DIR + i))) {
                    index++;
                }
                selection = selection.withOrderings(Arrays.asList(orderings[index]));
            }
            return SearchResultsAdapter.this.inResponseTo(selection);
        }

    }

    protected static <R extends Serializable, S extends Selection<R>> Selection<R> createSelection(
        final HttpServletRequest request, final SelectionFactory<R, S> selectionFactory) {
        final int startIndex = getIntParameter(request, JQueryDataTables.DISPLAY_START, 0);
        final int displayLength = getIntParameter(request, JQueryDataTables.DISPLAY_LENGTH, -1);
        return null == selectionFactory ? Selections.<R> slice(startIndex, displayLength) : Selections.slice(
            startIndex, displayLength, selectionFactory);
    }

    private final Converter<E> converter;

    private final SearchResultsProvider<E> provider;

    /**
     * Constructs new instance.
     *
     * @param provider search results provider
     * @param converter search results converter
     */
    public SearchResultsAdapter(final SearchResultsProvider<E> provider, final Converter<E> converter) {
        this.converter = converter;
        this.provider = provider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> inResponseTo(final HttpServletRequest request) {
        return inResponseTo(createSelection(request, null));
    }

    /**
     * Creates a Map holding all informations required by jQuery DataTables for viewing the data.
     *
     * @param selection
     * @return a Map holding all informations required by jQuery DataTables for viewing the data
     */
    @SuppressWarnings("boxing")
    protected <R extends Serializable> Map<String, Object> inResponseTo(final Selection<R> selection) {
        final SearchResults<E> results = provider.provideForSelection(selection);

        final Map<String, Object> result = new HashMap<>();
        result.put(JQueryDataTables.DATA, converter.apply(results));
        result.put(JQueryDataTables.TOTAL_NO, results.getTotalRecords());
        result.put(JQueryDataTables.FILTERED_NO, results.getTotalRecords());
        return result;
    }

    /**
     * Returns {@link TabularDataProvider} using given selection factory and orderings.
     *
     * @param selectionFactory selection factory
     * @param orderings orderings to be used
     * @return {@link TabularDataProvider} instance
     */
    @SafeVarargs
    @SuppressWarnings("PMD.UnnecessaryFinalModifier")
    public final <R extends Serializable, S extends Selection<R>> TabularDataProvider orderedWith(
        final SelectionFactory<R, S> selectionFactory, final R... orderings) {
        return new OrderedSearchResultsAdapter<>(selectionFactory, orderings);
    }

}
