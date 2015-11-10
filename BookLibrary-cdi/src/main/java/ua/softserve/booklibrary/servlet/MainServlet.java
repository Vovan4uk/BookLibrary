package ua.softserve.booklibrary.servlet;

import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MainServlet extends HttpServlet {

    public static final Class REVIEW_CLASS = Review.class;

    @EJB
    ReviewService reviewService;

    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Review review = reviewService.findReviewById(50L);

        //Review review = (Review) em.find(REVIEW_CLASS, 50L);

        PrintWriter out = resp.getWriter();
        out.print(review.getComment_rating());

    }

}