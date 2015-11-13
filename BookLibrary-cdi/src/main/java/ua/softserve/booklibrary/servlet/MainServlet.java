package ua.softserve.booklibrary.servlet;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.service.AuthorService;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@WebServlet ("/")
public class MainServlet extends HttpServlet {

    @EJB
    ReviewService reviewService;
    @EJB
    AuthorService authorService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
/*
        Author author = authorService.findAuthorById(1L);
        PrintWriter out = resp.getWriter();
        out.println(author);
*/
        Set<Author> authors = new HashSet<>();
        Author author = new Author();
        author.setFirstName("werwer");
        authors.add(author);
        author.setFirstName("09898");
        authors.add(author);
        PrintWriter out = resp.getWriter();
        out.println(authors.size());
/*
        List<Review> reviewList = reviewService.getAllReviews();
        PrintWriter out = resp.getWriter();
        out.println("All objects");
        for (Review review : reviewList) {
            out.println(review);
            out.println("     Mapped book " + review.getBook());
            out.println();
        }
*/
    }
}