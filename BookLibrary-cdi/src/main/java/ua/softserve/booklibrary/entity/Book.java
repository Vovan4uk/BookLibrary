package ua.softserve.booklibrary.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BOOK")
@NamedQueries({
		@NamedQuery(name = "Book.findHotReleases", query = "SELECT b FROM Book b ORDER BY createDate desc "),
		@NamedQuery(name = "Book.findBooksByRating", query = "SELECT b FROM Book b WHERE averageRating >= :minRating AND averageRating < :maxRating "),
		@NamedQuery(name = "Book.findBooksWithoutRating", query = "SELECT b FROM Book b WHERE averageRating IS NULL"),
		@NamedQuery(name = "Book.findLatestBooksByAuthorId", query = "SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.id = :id ORDER BY b.publishedDate DESC"),
		@NamedQuery(name = "Book.findBestBooksByAuthorId", query = "SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE b.averageRating IS NOT NULL AND a.id = :id ORDER BY b.averageRating DESC"),
		@NamedQuery(name = "Book.findBooksByAuthorId", query = "SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.id = :id"),
		@NamedQuery(name = "Book.findBooksByAuthors", query = "SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.id IN :ids"),
		@NamedQuery(name = "Book.isBookExistByIsbn", query = "SELECT CASE WHEN EXISTS (SELECT b FROM Book WHERE isbn = :isbn) THEN TRUE ELSE FALSE END FROM Book b"),
		@NamedQuery(name = "Book.isBookExistByIsbnWithId", query = "SELECT CASE WHEN EXISTS (SELECT b FROM Book WHERE isbn = :isbn AND id != :id) THEN TRUE ELSE FALSE END FROM Book b"),
		@NamedQuery(name = "Book.countBooksByRating", query = "SELECT COUNT(b) FROM Book b WHERE averageRating >= :minRating AND averageRating < :maxRating "),
		@NamedQuery(name = "Book.countBooksWithoutRating", query = "SELECT COUNT(b) FROM Book b WHERE averageRating IS NULL")
})
@XmlRootElement
public class Book extends LibraryEntity {
	private static final long serialVersionUID = 9073502830659864431L;
	@Id
	@SequenceGenerator(name = "BOOK_ID_GENERATOR", sequenceName = "BOOK_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GENERATOR")
	@Column(name = "ID", nullable = false)
	private Long id;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "NAME", nullable = false)
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "PUBLISHED_DATE")
	private Date publishedDate;

	@Size(min = 9, max = 255)
	@Column(name = "ISBN", unique = true)
	private String isbn;

	@Size(max = 255)
	@Column(name = "PUBLISHER")
	private String publisher;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	@OrderBy("createDate DESC")
	private Set<Review> reviews = new HashSet<>();

	@Formula("(SELECT AVG(r.RATING) FROM REVIEW r WHERE r.BOOK_ID = ID)")
	private Double averageRating;

	/* Count Reviews uses on all pages.
	Calculating count Reviews generates a large amount of code at all levels.
	It was decided to add an extra field. */
	@Formula("(SELECT COUNT(r.RATING) FROM REVIEW r WHERE r.BOOK_ID = ID)")
	private Integer countReviews;

	@ManyToMany
	@JoinTable(
			name = "BOOK_AUTHOR",
			joinColumns = @JoinColumn(name = "BOOK_ID"),
			inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
	@OrderBy("firstName DESC")
	private Set<Author> authors = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@XmlTransient
	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@XmlTransient
	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Integer getCountReviews() {
		return countReviews;
	}

	public void setCountReviews(Integer countReviews) {
		this.countReviews = countReviews;
	}

	/*
	 After '@Formula' has been calculated,
	 and the last one returned 'null',
	 set default averageRating to '0'.
	 Then, we shouldn't validate this param by 'null' on UI.
	 */
	@PostLoad
	private void onLoad() {
		if (averageRating == null) {
			averageRating = 0D;
		}
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", publishedDate=" + publishedDate +
				", isbn='" + isbn + '\'' +
				", publisher='" + publisher + '\'' +
				", averageRating=" + averageRating +
				", countReviews=" + countReviews +
				", createDate=" + super.getCreateDate() +
				'}';
	}
}