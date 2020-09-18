package org.jukbar.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "news")
public class News extends AbstractEntity<Integer> {

    private static final long serialVersionUID = 1L;
    private Date dateCreated;
    private Date dateNews;
    private String title;
    private String content;
	private Attachment image;
	private boolean active;

	@Column(name = "date_created")
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Column(name = "date_news")
	public Date getDateNews() {
		return dateNews;
	}
	public void setDateNews(Date dateNews) {
		this.dateNews = dateNews;
	}
	
	@Column(name = "title",length = 5000)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "content",length = 5000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne
    @JoinColumn(name = "image_id")
    public Attachment getImage() {
		return image;
	}
	public void setImage(Attachment image) {
		this.image = image;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

}
