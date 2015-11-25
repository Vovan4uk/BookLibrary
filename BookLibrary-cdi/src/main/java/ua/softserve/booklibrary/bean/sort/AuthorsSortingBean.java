package ua.softserve.booklibrary.bean.sort;

import org.richfaces.component.SortOrder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class AuthorsSortingBean implements Serializable {
    private static final long serialVersionUID = 8071118665737955020L;
    private Map<String, SortOrder> sortsOrders;
    private List<String> sortPriorities;

    private static final String SORT_PROPERTY_PARAMETER = "sortProperty";
    private static final String AVERAGE_RATING_SORT = "averageRating";
    private static final String CREATE_DATE_SORT = "createDate";

    public AuthorsSortingBean() {
        sortsOrders = new HashMap<>();
        sortPriorities = new ArrayList<>();

        //Default Sorting Parameters
        this.sortPriorities.add(AVERAGE_RATING_SORT);
        this.sortPriorities.add(CREATE_DATE_SORT);

        this.sortsOrders.put(AVERAGE_RATING_SORT, SortOrder.descending);
        this.sortsOrders.put(CREATE_DATE_SORT, SortOrder.descending);

    }

    public void sort() {
        String property = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get(SORT_PROPERTY_PARAMETER);

        if (property != null) {
            SortOrder currentPropertySortOrder = sortsOrders.get(property);

            if (!sortPriorities.contains(property)) {
                sortPriorities.add(property);
            }

            if (currentPropertySortOrder == null || currentPropertySortOrder.equals(SortOrder.descending)) {
                sortsOrders.put(property, SortOrder.ascending);
            } else {
                sortsOrders.put(property, SortOrder.descending);
            }
        }
    }

    public void reset(String param) {
        sortPriorities.remove(param);
        sortsOrders.remove(param);
    }

    public List<String> getSortPriorities() {
        return sortPriorities;
    }

    public Map<String, SortOrder> getSortsOrders() {
        return sortsOrders;
    }

}