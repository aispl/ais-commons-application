package pl.ais.commons.application.util.jquery.datatables;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Defines the API contract for tabular data provider.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public interface TabularDataProvider {

    /**
     * Creates a Map holding all informations required by jQuery DataTables for viewing tabular data.
     *
     * @param request currently serviced HTTP request
     * @return a Map holding all informations required by jQuery DataTables for viewing tabular data
     */
    Map<String, Object> inResponseTo(HttpServletRequest request);

}