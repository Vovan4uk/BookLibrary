package ua.softserve.booklibrary.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AUTHOR")
public class Author implements Serializable {
    @Id
    @SequenceGenerator(name = "AUTHOR_ID_GENERATOR", sequenceName = "AUTHOR_S")
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

    @Formula("(SELECT AVG(r.RATING) FROM AUTHOR a, BOOK_AUTHOR ba, BOOK b, REVIEW r WHERE a.ID = ID AND a.ID=ba.AUTHOR_ID AND ba.BOOK_ID=b.ID AND b.ID=r.BOOK_ID)")
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

    public Set<Book> getBooks() {
        return books;
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