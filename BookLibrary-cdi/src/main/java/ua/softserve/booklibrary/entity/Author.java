package ua.softserve.booklibrary.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
public class Author implements Serializable {
    @Id
    @SequenceGenerator(name = "AUTHOR_ID_GENERATOR", sequenceName = "author_s", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHOR_ID_GENERATOR")
    private Long id;

    private String first_name;

    private String second_name;

    @Temporal(TemporalType.DATE)
    private Date author_create;

    @ManyToMany(mappedBy="authors")
    private List<Book> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public Date getAuthor_create() {
        return author_create;
    }

    public void setAuthor_create(Date author_create) {
        this.author_create = author_create;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", author_create=" + author_create +
                '}';
    }
}