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
public class PublisherData {
	private Long publisherId;
	private String name;
	private String parentCompany;
	private String country;
	private String revenue;
	private Set<BookResponse> books = new HashSet<BookResponse>();
	
	public PublisherData(Publisher publisher) {
		this.publisherId = publisher.getPublisherId();
		this.name = publisher.getName();
		this.parentCompany = publisher.getParentCompany();
		this.country = publisher.getCountry();
		this.revenue = publisher.getRevenue();
		
		for(Book book: publisher.getBooks()) {
			books.add(new BookResponse(book));
		}
		
	}
	
	@Data
	@NoArgsConstructor
	static class BookResponse{
		private Long bookId;
		private String bookTitle;
		private String author;
		private String yearPublished;
		private String isbn;
		
		private Set<String> genres = new HashSet<String>();
		
		BookResponse(Book book){
			bookId = book.getBookId();
			bookTitle = book.getBookTitle();
			author = book.getBookAuthor();
			yearPublished = book.getYearPublished();
			isbn = book.getIsbn();
			
			for(Genre genre: book.getGenres()) {
				genres.add(genre.getName());
			}
		}
	}
}

