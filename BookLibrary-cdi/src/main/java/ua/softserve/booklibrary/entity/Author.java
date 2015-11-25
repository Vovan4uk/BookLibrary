package ua.softserve.booklibrary.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "AUTHOR")
@NamedQueries({
        @NamedQuery(name = "Author.findAuthorsByRating", query = "SELECT a FROM Author a WHERE averageRating >= :minRating AND averageRating < :maxRating "),
        @NamedQuery(name = "Author.findAuthorsWithoutRating", query = "SELECT a FROM Author a WHERE averageRating IS NULL")
})

public class Author extends Entity {
    private static final long serialVersionUID = 5544814440011028323L;
    @Id
    @SequenceGenerator(name = "AUTHOR_ID_GENERATOR", sequenceName = "AUTHOR_S", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHOR_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Max(value = 255)
    @Column(name = "SECOND_NAME")
    private String secondName;

    /*
        Maybe fix later
        javax.servlet.ServletException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet
        @Formula("(SELECT AVG(r.RATING) FROM (SELECT ba.BOOK_ID FROM BOOK_AUTHOR ba WHERE ba.AUTHOR_ID = 1) bb LEFT JOIN REVIEW r ON r.BOOK_ID = bb.BOOK_ID)")
    */
    @Formula("(SELECT AVG(r.RATING) FROM Review r, BOOK_AUTHOR ba WHERE r.BOOK_ID = ba.BOOK_ID AND ba.AUTHOR_ID = ID)")
    private Double averageRating;

    @ManyToMany(mappedBy = "authors")
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
                ", averageRating=" + averageRating +
                '}';
    }
}