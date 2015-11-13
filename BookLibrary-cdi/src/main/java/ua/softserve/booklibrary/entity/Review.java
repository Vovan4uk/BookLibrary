package ua.softserve.booklibrary.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REVIEW")
public class Review implements Serializable {

    private static final int MIN_COMMENTER_NAME_SIZE = 2;
    private static final int MAX_COMMENTER_NAME_SIZE = 100;
    private static final int MIN_COMMENT_BODY_SIZE = 2;
    private static final int MAX_COMMENT_BODY_SIZE = 2000;
    private static final int MIN_RATING_SIZE = 1;
    private static final int MAX_RATING_SIZE = 5;

    @Id
    @SequenceGenerator(name = "REVIEW_ID_GENERATOR", sequenceName = "REVIEW_S")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @Size(min = MIN_COMMENTER_NAME_SIZE, max = MAX_COMMENTER_NAME_SIZE)
    @Column(name = "COMMENTER_NAME", nullable = false)
    private String commenterName;

    @NotNull
    @Lob
    @Size(min = MIN_COMMENT_BODY_SIZE, max = MAX_COMMENT_BODY_SIZE)
    @Column(name = "COMMENT_BODY", nullable = false)
    private String commentBody;

    @NotNull
    @Min(MIN_RATING_SIZE)
    @Max(MAX_RATING_SIZE)
    @Column(name = "RATING", nullable = false)
    private Integer rating;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    private Date createDate;

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

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", commenterName='" + commenterName + '\'' +
                ", commentBody='" + commentBody + '\'' +
                ", rating=" + rating +
                ", createDate=" + createDate +
                '}';
    }
}