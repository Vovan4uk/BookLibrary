package ua.softserve.booklibrary.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@javax.persistence.Entity
@Table(name = "REVIEW")
@NamedQueries({
        @NamedQuery(name = "Review.countBookReviews", query = "SELECT COUNT(r) as r_count FROM Review r WHERE r.book = :book")
})
public class Review extends Entity {
    private static final long serialVersionUID = -8631161684971086224L;
    @Id
    @SequenceGenerator(name = "REVIEW_ID_GENERATOR", sequenceName = "REVIEW_S", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "COMMENTER_NAME", nullable = false)
    private String commenterName;

    @NotNull
    @Lob
    @Size(min = 2, max = 20000)
    @Column(name = "COMMENT_BODY", nullable = false, length = 20000)
    private String commentBody;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "RATING", nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public Review() {
    }

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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", commenterName='" + commenterName + '\'' +
                ", commentBody='" + commentBody + '\'' +
                ", rating=" + rating +
                '}';
    }
}