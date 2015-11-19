package ua.softserve.booklibrary.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "AUTHOR")
public class Author implements Serializable, EntityInterface {
    private static final long serialVersionUID = 5544814440011028323L;
    @Id
    @SequenceGenerator(name = "AUTHOR_ID_GENERATOR", sequenceName = "AUTHOR_S", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHOR_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "SECOND_NAME")
    private String secondName;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    private Date createDate;
    /*
        @Formula("(SELECT AVG(r.RATING) " +
                "FROM Review r " +
                "LEFT JOIN BOOK_AUTHOR ba " +
                "ON a.ID = ba.AUTHOR_ID " +
                "LEFT JOIN BOOK b " +
                "ON ba.BOOK_ID = b.ID " +
                "LEFT JOIN REVIEW r ON b.ID = r.BOOK_ID)")
    */
    @Formula("(SELECT AVG(r.RATING) FROM Review r, BOOK_AUTHOR ba WHERE r.BOOK_ID = ba.BOOK_ID AND ba.AUTHOR_ID = ID)")
    private Double averageRating;

    @ManyToMany(mappedBy="authors")
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", createDate=" + createDate +
                ", averageRating=" + averageRating +
                '}';
    }
}