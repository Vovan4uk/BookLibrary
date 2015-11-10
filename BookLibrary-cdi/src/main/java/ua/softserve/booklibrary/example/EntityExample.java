package ua.softserve.booklibrary.example;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.*;

public class EntityExample {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BookLibrary");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Review review = new Review();
        review.setComment_body("review body");
        review.setCommenter_name("commenter name");
        review.setComment_create(new Date());
        review.setComment_rating(5);

        List<Review> reviewSet = new ArrayList<>();
        reviewSet.add(review);


        Book book = new Book();
        book.setBook_create(new Date());
        book.setIsbn("ISBN2015-ORM9");
        book.setName("book name");
        book.setPublished_date(new Date());
        book.setPublisher("publisher name");

        List<Book> bookSet = new ArrayList<>();
        bookSet.add(book);


        Author author = new Author();
        author.setId(1L);
/*
        author.setAuthor_create(new Date());
        author.setFirst_name("first name");
        author.setSecond_name("second name");
*/

        List<Author> authorSet = new ArrayList<>();
        authorSet.add(author);



        review.setBook(book);
        book.setReviews(reviewSet);
        book.setAuthors(authorSet);
//        author.setBooks(bookSet);

        em.persist(book);
//        em.persist(author);

        tx.commit();
/*
        ServiceExample serviceExample = new ServiceExample();
        serviceExample.setReview();
*/
    }}
