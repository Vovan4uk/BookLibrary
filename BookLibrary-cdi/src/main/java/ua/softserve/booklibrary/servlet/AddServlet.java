package ua.softserve.booklibrary.servlet;

import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.dao.home.ReviewHome;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;

@WebServlet ("/add")
public class AddServlet extends HttpServlet {

    private static final long serialVersionUID = -7709667022839878773L;
    private static ValidatorFactory vf;
    private static Validator validator;

    @EJB
    ReviewHome reviewHome;

    @EJB
    ReviewFacade reviewFacade;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();


/*
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        if (violations.size() > 0) {
            PrintWriter out = resp.getWriter();
            out.print(violations);
        } else {
            reviewService.save(review);
            reviewHome.save(review);
            PrintWriter out = resp.getWriter();
            out.print("New Object added " + review);
        }
*/
//        Review review1 = reviewFacade.findByPk(1L);
        reviewHome.removeByPk(2L);

    }

}