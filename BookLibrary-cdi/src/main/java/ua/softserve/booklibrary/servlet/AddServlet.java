package ua.softserve.booklibrary.servlet;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Set;

@WebServlet ("/add")
public class AddServlet extends HttpServlet {

    private static ValidatorFactory vf;
    private static Validator validator;

    @EJB
    ReviewService reviewService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();

        Review review = new Review();
        review.setCommentBody("new test review body");
        review.setCommenterName("commenter name test validation");
        review.setCommentCreate(new Date());
        review.setCommentRating(3);

        Book book = new Book();
        book.setId(1L);
        review.setBook(book);

        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        if (violations.size() > 0) {
            PrintWriter out = resp.getWriter();
            out.print(violations);
        } else {
            reviewService.saveReview(review);
            PrintWriter out = resp.getWriter();
            out.print("New Object added " + review);
        }

    }

}