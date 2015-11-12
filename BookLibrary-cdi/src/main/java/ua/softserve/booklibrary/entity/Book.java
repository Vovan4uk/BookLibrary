package ua.softserve.booklibrary.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
public class Book implements Serializable {
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
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="BOOK_AUTHOR",
            joinColumns=@JoinColumn(name="BOOK_ID"),
            inverseJoinColumns=@JoinColumn(name="AUTHOR_ID"))
    private List<Author> authors = new ArrayList<>();

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", published_date=" + published_date +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", book_create=" + book_create +
                '}';
    }
}