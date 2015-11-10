package ua.softserve.booklibrary.servlet;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class MainServlet extends HttpServlet {

    @EJB
    ReviewService reviewService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Review review = new Review();
        review.setComment_body("new test review body");
        review.setCommenter_name("new test commenter name");
        review.setComment_create(new Date());
        review.setComment_rating(3);

        Book book = new Book();
        book.setId(34L);
        review.setBook(book);

        reviewService.saveReview(review);

//        Review review = reviewService.findReviewById(50L);

        PrintWriter out = resp.getWriter();
        out.print(review);

    }

}