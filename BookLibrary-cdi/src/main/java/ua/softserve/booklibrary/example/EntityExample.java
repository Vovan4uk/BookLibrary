package ua.softserve.booklibrary.example;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

        Set<Review> reviewSet = new HashSet<Review>();
        reviewSet.add(review);


        Book book = new Book();
        book.setBook_create(new Date());
        book.setIsbn("ISBN2015-ORM5");
        book.setName("book name");
        book.setPublished_date(new Date());
        book.setPublisher("publisher name");

        book.setReviews(reviewSet);
        review.setBook(book);

        System.out.println("Book_ID --------------" + book.getId());
        System.out.println("Review_ID ------------" + review.getId());

        em.persist(book);

        System.out.println("Book_ID --------------" + book.getId());
        System.out.println("Review_ID ------------"+review.getId());
        System.out.println("Review  Book_ID ------------"+review.getBook().getId());

        tx.commit();
/*
        ServiceExample serviceExample = new ServiceExample();
        serviceExample.setReview();
*/
    }}
