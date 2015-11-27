package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.manager.ReviewManager;

import javax.ejb.EJB;

/*
@Named
@ViewScoped
*/
public class ReviewAction {

    @EJB
    private ReviewManager reviewManager;

    private String testString = "hoh";

    public ReviewAction() {
    }

    public String save() {
        return "test.xhtml";
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
}
