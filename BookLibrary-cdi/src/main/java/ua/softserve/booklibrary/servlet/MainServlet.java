package ua.softserve.booklibrary.servlet;

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
import java.util.List;

@WebServlet ("/")
public class MainServlet extends HttpServlet {

    @EJB
    ReviewService reviewService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Review> reviewList = reviewService.getAllReviews();
        PrintWriter out = resp.getWriter();
        out.println("All objects");
        for (Review review : reviewList) {
            out.println(review);
            out.println("     Mapped book " + review.getBook());
            out.println();
        }
    }

}