package ua.softserve.booklibrary.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "BOOK")
@NamedQueries({
        @NamedQuery(name = "Book.findHotReleases", query = "SELECT b FROM Book b ORDER BY createDate DESC")
})

public class Book implements Serializable, EntityInterface {
    private static final long serialVersionUID = 9073502830659864431L;
    @Id
    @SequenceGenerator(name = "BOOK_ID_GENERATOR", sequenceName = "BOOK_S", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "PUBLISHED_DATE")
    private Date publishedDate;

    @Column(name = "ISBN", unique = true)
    private String isbn;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    private Date createDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    @OrderBy("createDate DESC")
    private Set<Review> reviews = new HashSet<>();

    @Formula("(SELECT AVG(r.RATING) FROM REVIEW r WHERE r.BOOK_ID = ID)")
    private Double averageRating;

    @ManyToMany
    @JoinTable(
            name="BOOK_AUTHOR",
            joinColumns=@JoinColumn(name="BOOK_ID"),
            inverseJoinColumns=@JoinColumn(name="AUTHOR_ID"))
    private Set<Author> authors = new HashSet<>();

    public Book() {
    }

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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publishedDate=" + publishedDate +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", createDate=" + createDate +
                ", averageRating=" + averageRating +
                '}';
    }
}