package pl.ais.commons.application.util.jquery.datatables;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public interface TabularDataProvider {

    /**
     * Creates a Map holding all informations required by jQuery DataTables for viewing the data.
     *
     * @param request currently serviced HTTP request
     * @return a Map holding all informations required by jQuery DataTables for viewing the data
     */
    Map<String, Object> inResponseTo(HttpServletRequest request);

}