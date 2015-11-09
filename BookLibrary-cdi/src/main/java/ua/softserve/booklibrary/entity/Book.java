package ua.softserve.booklibrary.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Book {
    @Id
    @SequenceGenerator(name = "BOOK_ID_GENERATOR", sequenceName = "book_s", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GENERATOR")
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date published_date;

    private String isbn;

    private String publisher;

    @Temporal(TemporalType.DATE)
    private Date book_create;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<Review>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublished_date() {
        return published_date;
    }

    public void setPublished_date(Date published_date) {
        this.published_date = published_date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getBook_create() {
        return book_create;
    }

    public void setBook_create(Date book_create) {
        this.book_create = book_create;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}