package bookstore.controller.model;

import java.util.HashSet;
import java.util.Set;

import bookstore.entity.Genre;
import bookstore.entity.Book;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class GenreData {
	private Long genreId;
	private String name;
	
	public Set<String> books = new HashSet<String>();
	
	public GenreData(Genre genre) {
		genreId = genre.getGenreId();
		this.name = genre.getName();
		
		for(Book book: genre.getBooks()) {
			books.add(book.getBookTitle());
		}
	}
	
}
