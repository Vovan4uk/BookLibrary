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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet ("/add")
public class AddServlet extends HttpServlet {

    @EJB
    ReviewService reviewService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Review review = new Review();
        review.setCommentBody("new test review body");
        review.setCommenterName("commenter name");
        review.setCommentCreate(new Date());
        review.setCommentRating(3);

        Book book = new Book();
        book.setId(1L);
        review.setBook(book);

        reviewService.saveReview(review);

        PrintWriter out = resp.getWriter();
        out.print("New Object added "+review);

    }

}