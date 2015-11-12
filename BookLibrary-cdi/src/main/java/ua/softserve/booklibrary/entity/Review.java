package ua.softserve.booklibrary.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Review implements Serializable {
    @Id
    @SequenceGenerator(name = "REVIEW_ID_GENERATOR", sequenceName = "review_s", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_ID_GENERATOR")
    private Long id;

    private String commenter_name;

    private String comment_body;

    private Integer comment_rating;

    @Temporal(TemporalType.DATE)
    private Date comment_create;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommenter_name() {
        return commenter_name;
    }

    public void setCommenter_name(String commenter_name) {
        this.commenter_name = commenter_name;
    }

    public String getComment_body() {
        return comment_body;
    }

    public void setComment_body(String comment_body) {
        this.comment_body = comment_body;
    }

    public Integer getComment_rating() {
        return comment_rating;
    }

    public void setComment_rating(Integer comment_rating) {
        this.comment_rating = comment_rating;
    }

    public Date getComment_create() {
        return comment_create;
    }

    public void setComment_create(Date comment_create) {
        this.comment_create = comment_create;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Review() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (!commenter_name.equals(review.commenter_name)) return false;
        if (!comment_body.equals(review.comment_body)) return false;
        if (!comment_rating.equals(review.comment_rating)) return false;
        return comment_create.equals(review.comment_create);

    }

    @Override
    public int hashCode() {
        int result = commenter_name.hashCode();
        result = 31 * result + comment_body.hashCode();
        result = 31 * result + comment_rating.hashCode();
        result = 31 * result + comment_create.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", commenter_name='" + commenter_name + '\'' +
                ", comment_body='" + comment_body + '\'' +
                ", comment_rating=" + comment_rating +
                ", comment_create=" + comment_create +
                '}';
    }
}