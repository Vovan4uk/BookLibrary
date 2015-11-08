package ua.softserve.booklibrary.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
public class Review {
    @Id
    @SequenceGenerator(name = "REVIEW_ID_GENERATOR", sequenceName = "review_s", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_ID_GENERATOR")
    Long id;

    String commenter_name;

    String comment_body;

    Integer comment_rating;

    @Temporal(TemporalType.DATE)
    Date comment_create;

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
}