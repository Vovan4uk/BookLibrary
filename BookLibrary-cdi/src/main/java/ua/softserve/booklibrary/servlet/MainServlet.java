package ua.softserve.booklibrary.servlet;

import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.entity.Review;

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
    ReviewFacade reviewFacade;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
/*
        Author author = authorService.findAuthorById(1L);
        PrintWriter out = resp.getWriter();
        out.println(author);
*/
/*
        Set<Author> authors = new HashSet<>();
        Author author = new Author();
        author.setFirstName("werwer");
        authors.add(author);
        author.setFirstName("09898");
        authors.add(author);
        PrintWriter out = resp.getWriter();
        out.println(authors.size());
*/
        List<Review> reviewList = reviewFacade.findAll();
        PrintWriter out = resp.getWriter();
        out.println("All objects");
        for (Review review : reviewList) {
            out.println(review);
            out.println("     Mapped book " + review.getBook());
            out.println();
        }
    }
}