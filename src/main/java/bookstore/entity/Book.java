package bookstore.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	@EqualsAndHashCode.Exclude
	private String bookTitle;
	
	@EqualsAndHashCode.Exclude
	private String bookAuthor;

	@EqualsAndHashCode.Exclude
	private String yearPublished;

	@EqualsAndHashCode.Exclude
	private String isbn;
	

//many books have one author
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "publisher_id", nullable = false)
	private Publisher publisher;

	// We don't want to delete rows out of the author table if a book is deleted
	// but we don't want to delete rows out of the join table
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "book_genre", 
	joinColumns = @JoinColumn(name = "book_id") 
	,inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> genres = new HashSet<Genre>();

}