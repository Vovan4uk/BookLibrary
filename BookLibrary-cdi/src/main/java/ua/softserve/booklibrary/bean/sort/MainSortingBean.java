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
public class MainSortingBean implements Serializable {
	private static final long serialVersionUID = -7944021928506372310L;
	private final Map<String, SortOrder> sortsOrders; //todo: final - fixed
	private final List<String> sortPriorities;    //todo: final - fixed

	private static final String SORT_PROPERTY_PARAMETER = "sortProperty";
	private static final String AVERAGE_RATING_SORT = "averageRating";
	private static final String CREATE_DATE_SORT = "createDate";

	public MainSortingBean() {
		sortsOrders = new HashMap<>();
		sortPriorities = new ArrayList<>();

		//Default Sorting Parameters
		sortPriorities.add(AVERAGE_RATING_SORT);
		sortPriorities.add(CREATE_DATE_SORT);

		sortsOrders.put(AVERAGE_RATING_SORT, SortOrder.descending);
		sortsOrders.put(CREATE_DATE_SORT, SortOrder.descending);
	}

	public void sort() {
		String property = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get(SORT_PROPERTY_PARAMETER);

		if (property != null) {
			SortOrder currentPropertySortOrder = sortsOrders.get(property);
			reset();
			if (currentPropertySortOrder == null || currentPropertySortOrder == SortOrder.descending) {
				sortsOrders.put(property, SortOrder.ascending);
			} else {
				sortsOrders.put(property, SortOrder.descending);
			}
		}
	}

	private void reset() {
		sortPriorities.clear();
		sortsOrders.clear();
	}

	public List<String> getSortPriorities() {
		return sortPriorities;
	}

	public Map<String, SortOrder> getSortsOrders() {
		return sortsOrders;
	}

}