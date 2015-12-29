package ua.softserve.booklibrary.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Parent for all entities encapsulated create date
 */
@MappedSuperclass
public abstract class LibraryEntity implements Serializable {

	private static final long serialVersionUID = 4236384240741049123L;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	private Date createDate;

	public abstract Long getId();

	@PrePersist
	protected void onCreate() {
		createDate = new Date();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "LibraryEntity{" +
				"createDate=" + createDate +
				'}';
	}
}