package ua.softserve.booklibrary.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Review implements Serializable {

    private static final int MIN_COMMENTER_NAME_SIZE = 2;
    private static final int MAX_COMMENTER_NAME_SIZE = 20;
    private static final int MIN_COMMENT_BODY_SIZE = 2;
    private static final int MAX_COMMENT_BODY_SIZE = 2000;
    private static final int MIN_RATING_SIZE = 1;
    private static final int MAX_RATING_SIZE = 5;

    @Id
    @SequenceGenerator(name = "REVIEW_ID_GENERATOR", sequenceName = "review_s", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_ID_GENERATOR")
    private Long id;

    @NotNull
    @Size(min = MIN_COMMENTER_NAME_SIZE, max = MAX_COMMENTER_NAME_SIZE)
    @Column(name = "commenter_name")
    private String commenterName;

    @NotNull
    @Lob
    @Size(min = MIN_COMMENT_BODY_SIZE, max = MAX_COMMENT_BODY_SIZE)
    @Column(name = "comment_body")
    private String commentBody;

    @NotNull
    @Min(MIN_RATING_SIZE)
    @Max(MAX_RATING_SIZE)
    @Column(name = "comment_rating")
    private Integer commentRating;

    @Temporal(TemporalType.DATE)
    @Column(name = "comment_create")
    private Date commentCreate;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenter_name) {
        this.commenterName = commenter_name;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String comment_body) {
        this.commentBody = comment_body;
    }

    public Integer getCommentRating() {
        return commentRating;
    }

    public void setCommentRating(Integer comment_rating) {
        this.commentRating = comment_rating;
    }

    public Date getCommentCreate() {
        return commentCreate;
    }

    public void setCommentCreate(Date comment_create) {
        this.commentCreate = comment_create;
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

        if (!commenterName.equals(review.commenterName)) return false;
        if (!commentBody.equals(review.commentBody)) return false;
        if (!commentRating.equals(review.commentRating)) return false;
        return commentCreate.equals(review.commentCreate);

    }

    @Override
    public int hashCode() {
        int result = commenterName.hashCode();
        result = 31 * result + commentBody.hashCode();
        result = 31 * result + commentRating.hashCode();
        result = 31 * result + commentCreate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", commenterName='" + commenterName + '\'' +
                ", commentBody='" + commentBody + '\'' +
                ", commentRating=" + commentRating +
                ", commentCreate=" + commentCreate +
                '}';
    }
}