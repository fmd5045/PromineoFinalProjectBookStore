package bookstore.controller.model;

import java.util.HashSet;
import java.util.Set;

import bookstore.entity.Book;
import bookstore.entity.Genre;
import bookstore.entity.Publisher;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookData {
	private Long bookId;
	private String bookTitle;
	private String bookAuthor;
	private String yearPublished;
	private String isbn;
	private BookPublisher publisher;
	private Set<String> genres = new HashSet<String>();

	public BookData(Book book) {
		bookId = book.getBookId();
		bookTitle = book.getBookTitle();
		bookAuthor = book.getBookAuthor();
		yearPublished = book.getYearPublished();
		isbn = book.getIsbn();
		publisher = new BookPublisher(book.getPublisher());

		for (Genre genre : book.getGenres()) {
			genres.add(genre.getName());
		}
	}
	

	@Data
	@NoArgsConstructor
	public static class BookPublisher {
		private Long publisherId;
		private String name;
		private String parentCompany;
		private String country;
		private String revenue;

		public BookPublisher(Publisher publisher) {
			publisherId = publisher.getPublisherId();
			name = publisher.getName();
			parentCompany = publisher.getParentCompany();
			country = publisher.getCountry();
			revenue = publisher.getRevenue();
		}
	}
}
