package bookstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bookstore.controller.model.BookData;
import bookstore.controller.model.PublisherData;
import bookstore.entity.Genre;
import bookstore.service.BookStoreService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book_store")
@Slf4j
public class BookStoreController {
	@Autowired
	private BookStoreService bookStoreService;

	//PUBLISHER ROUTES
	@PostMapping("/publisher")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PublisherData createPublisher(@RequestBody PublisherData publisherData) {
		log.info("Creating a publisher {}", publisherData);
		return bookStoreService.savePublisher(publisherData);
	}
	
	@PutMapping("/publisher/{publisherId}")
	public PublisherData updatePublisher(@PathVariable Long publisherId,
			@RequestBody PublisherData publisherData) {
		publisherData.setPublisherId(publisherId);
		log.info("Updating publisher{}", publisherData);
		return bookStoreService.savePublisher(publisherData);
	}

	@GetMapping("/publisher")
	public List<PublisherData> retrieveAllPublishers() {
		log.info("Retrieve all publishers called.");
		return bookStoreService.retrieveAllPublishers();
	}

	@GetMapping("/publisher/{publisherId}")
	public PublisherData retrievePublisherById(@PathVariable Long publisherId) {
		log.info("Retrieving publisher with ID={}", publisherId);
		return bookStoreService.retrievePublisherById(publisherId);
	}

	@DeleteMapping("/publisher")
	public void deleteAllPublishers() {
		log.info("Attempting to delete all publishers.");
		throw new UnsupportedOperationException("Deleting all publishers is NOT allowed.");
	}

	@DeleteMapping("/publisher/{publisherId}")
	public Map<String, String> deletePublisherById(@PathVariable Long publisherId) {
		log.info("Deleting publisher with ID= {}" + publisherId);

		bookStoreService.deletePublisherById(publisherId);

		return Map.of("message", "Deletion the publisher with ID= " + publisherId + " was successfull.");

	}
	
	//BOOK ROUTES

	@PostMapping("/publisher/{publisherId}/book")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookData insertBook(@PathVariable Long publisherId, @RequestBody BookData bookData) {
		log.info("Creating book {} for publisher with ID= {}", bookData, publisherId);
		return bookStoreService.saveBook(publisherId, bookData);
	}
	
	@GetMapping("/publisher/{publisherId}/book/{bookId}")
	public BookData retrieveBookById(@PathVariable Long publisherId,
			@PathVariable Long bookId){
		log.info("Retrieving book with ID= {} for publisher with ID= {}", bookId, publisherId);	
		return bookStoreService.retrieveBookById(publisherId, bookId);
	}
	
	@PutMapping("/publisher/{publisherId}/book/{bookId}")
	public BookData updateBook(@PathVariable Long publisherId,
			@PathVariable Long bookId,
			@RequestBody BookData bookData) {

		bookData.setBookId(bookId);
		log.info("Creating book {} for publisher with ID= {}", bookData,publisherId);

		return bookStoreService.saveBook(publisherId, bookData);
	}
	
	@DeleteMapping("/book/{bookId}")
	public Map<String, String> deleteBookById(@PathVariable Long bookId) {
		log.info("Deleting book with ID= {}" + bookId);

		bookStoreService.deleteBookById(bookId);

		return Map.of("message", "Deletion the book with ID= " + bookId + " was successfull.");

	}
	
	@DeleteMapping("/book")
	public void deleteAllBooks() {
		log.info("Attempting to delete all books.");
		throw new UnsupportedOperationException("Deleting all books is NOT allowed.");
	}
	
	//GENRE ROUTES
	
	@GetMapping("/genre")
	public List<Genre> retrieveAllGenre() {
		log.info("Retrieve all genre called.");
		return bookStoreService.retrieveAllGenres();
	}
	
	
	@DeleteMapping("/genre/{genreId}")
	public Map<String, String> deleteGenreById(@PathVariable Long genreId) {
		log.info("Deleting genre with ID= {}" + genreId);

		bookStoreService.deleteGenreById(genreId);

		return Map.of("message", "Deletion the genre with ID= " + genreId + " was successfull.");

	}
	
	@DeleteMapping("/genre")
	public void deleteAllGenres() {
		log.info("Attempting to delete all genres.");
		throw new UnsupportedOperationException("Deleting all genres is NOT allowed.");
	}
}