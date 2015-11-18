package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.manager.ReviewManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@Stateless
public class ReviewController {

    @EJB
    ReviewManager reviewManager;

    private String testString = "hoh";

    public ReviewController() {
    }

    public String save() {
        String msg = "test";
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return "test.xhtml";
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
}
