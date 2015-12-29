package ua.softserve.booklibrary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "REVIEW")
@XmlRootElement
public class Review extends LibraryEntity {
    private static final long serialVersionUID = -8631161684971086224L;
    @Id
    @SequenceGenerator(name = "REVIEW_ID_GENERATOR", sequenceName = "REVIEW_S", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "COMMENTER_NAME", nullable = false)
    private String commenterName;

    @NotNull
    @Lob
    @Column(name = "COMMENT_BODY", nullable = false, length = 20000)
    private String commentBody;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "RATING", nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    @XmlTransient
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

    @XmlTransient
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
                ", createDate=" + super.getCreateDate() +
                '}';
    }
}