package pl.ais.commons.application.util.jquery.datatables;

import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.ais.commons.query.AbstractSelection;
import pl.ais.commons.query.SearchResults;
import pl.ais.commons.query.Selection;
import pl.ais.commons.query.SelectionFactory;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static junit.framework.Assert.assertEquals;

/**
 * Verifies {@link TabularData} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("static-method")
public class TabularDataExpectations {

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private static SearchResultsConverter<Integer> createConverter() {
        return results -> {
            final List<Object[]> result = new ArrayList<>();
            for (final Integer element : results.getElements()) {
                result.add(new Object[] {element});
            }
            return result.toArray(new Object[result.size()][1]);
        };
    }

    private static SearchResultsProvider<Integer> createSearchResultsProvider() {
        return selection -> {
            final List<Integer> results = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                results.add(Integer.valueOf(i));
            }
            for (final Object comparator : selection.getOrderings()) {
                Collections.sort(results, (Comparator<? super Integer>) comparator);
            }

            final int from = selection.getStartIndex();
            return SearchResults.of(100, results.subList(from,
                Math.min(selection.isSelectingSubset() ? from + selection.getDisplayLength() : 100, 100)));
        };
    }

    /**
     * Verifies if ordering selected by the user is applied to the data.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void shouldApplyOrderingToTheData() {

        // Given Search Results provider, data converter and HTTP request for ordered tabular data, ...
        final SearchResultsProvider<Integer> provider = createSearchResultsProvider();
        final SearchResultsConverter<Integer> converter = createConverter();

        final HttpServletRequest request = MockMvcRequestBuilders.get("/doit.html")
                                                                 .param(JQueryDataTables.DISPLAY_START, "10").param(JQueryDataTables.DISPLAY_LENGTH, "5")
                                                                 .param(JQueryDataTables.SORT_COLS_NO, "1").param(JQueryDataTables.SORT_COL_PREFIX + "0", "0")
                                                                 .buildRequest(new MockServletContext());

        // ... when we build the tabular data in response to the request, ...
        final Map<String, Object> result = TabularData.adaptSearchResults(provider, converter).orderedWith(
            new CustomSelectionFactory(),
            new ReversingComparator[][] {new ReversingComparator[] {new ReversingComparator<Integer>()}}).inResponseTo(request);

        // ... then data should start with row holding number 89.
        final Object[][] data = (Object[][]) result.get(JQueryDataTables.DATA);
        assertEquals("First result selected should be 89.", Integer.valueOf(89), data[0][0]);
    }

    /**
     * Verifies if selection provided by user is applied to the data.
     */
    @Test
    public void shouldApplySelectionToTheData() {

        // Given Search Results provider, data converter and HTTP request for tabular data, ...
        final SearchResultsProvider<Integer> provider = createSearchResultsProvider();
        final SearchResultsConverter<Integer> converter = createConverter();

        final HttpServletRequest request = MockMvcRequestBuilders.get("/doit.html")
                                                                 .param(JQueryDataTables.DISPLAY_START, "10").param(JQueryDataTables.DISPLAY_LENGTH, "5")
                                                                 .buildRequest(new MockServletContext());

        // ... when we build the tabular data in response to the request, ...
        final Map<String, Object> result = TabularData.adaptSearchResults(provider, converter).inResponseTo(request);

        // ... then data should start with row holding number 10.
        final Object[][] data = (Object[][]) result.get(JQueryDataTables.DATA);
        assertEquals("First result selected should be 10.", Integer.valueOf(10), data[0][0]);
    }

    /**
     * Custom Selection.
     */
    @SuppressWarnings("serial")
    private class CustomSelection extends AbstractSelection<ReversingComparator> {

        public CustomSelection(final int startIndex, final int displayLength, @Nonnull final ReversingComparator... orderings) {
            super(startIndex, displayLength, orderings);
        }

        @Override
        public Selection<ReversingComparator> withOrderings(@Nonnull final Collection<ReversingComparator> orderings) {
            final List<ReversingComparator> modified = Arrays.stream(getOrderings())
                                                             .filter(ordering -> !orderings.contains(ordering))
                                                             .collect(toList());
            modified.addAll(orderings);

            return new CustomSelection(getStartIndex(), getDisplayLength(), modified.toArray(new ReversingComparator[modified.size()]));
        }

    }

    /**
     * Custom Selection Factory.
     */
    class CustomSelectionFactory implements
        SelectionFactory<ReversingComparator, CustomSelection> {

        /**
         * {@inheritDoc}
         */
        @Override
        public CustomSelection createSelection(final int startIndex, final int displayLength, final ReversingComparator... orderings) {
            return new CustomSelection(startIndex, displayLength, orderings);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<? extends ReversingComparator> getOrderingType() {
            return ReversingComparator.class;
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    class ReversingComparator<T extends Comparable<T>> implements Comparator<T>, Serializable {

        @Override
        public int compare(final T object1, final T object2) {
            return -object1.compareTo(object2);
        }
    }

}
