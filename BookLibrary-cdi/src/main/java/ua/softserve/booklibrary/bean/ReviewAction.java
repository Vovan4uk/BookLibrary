package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.manager.ReviewManager;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ReviewAction implements Serializable {

    private static final long serialVersionUID = -7364126515288020438L;
    @EJB
    private ReviewManager reviewManager;

}
