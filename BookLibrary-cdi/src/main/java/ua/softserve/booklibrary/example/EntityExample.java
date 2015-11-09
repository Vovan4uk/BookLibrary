package ua.softserve.booklibrary.example;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class EntityExample {
    public static void main(String[] args){

        System.out.println("sdfsdf");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BookLibrary");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Review review = new Review();
        review.setComment_body("sdfsdfdfgdfgert");
        review.setCommenter_name("sdfsdfop");
        review.setComment_create(new Date());
        review.setComment_rating(5);
        review.setBook(new Book());

        Review review2 = new Review();
        review2.setComment_body("uiouioyui");
        review2.setCommenter_name("uiouiouioiop");
        review2.setComment_create(new Date());
        review2.setComment_rating(4);

        System.out.println(review.getId()+"--------------");
        em.persist(review);
        em.persist(review2);
        System.out.println(review.getId()+"++++++++++++++");

        tx.commit();
/*
        ServiceExample serviceExample = new ServiceExample();
        serviceExample.setReview();
*/
    }}
