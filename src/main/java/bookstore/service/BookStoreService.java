package bookstore.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookstore.controller.model.BookData;
import bookstore.controller.model.PublisherData;
import bookstore.dao.BookDao;
import bookstore.dao.GenreDao;
import bookstore.dao.PublisherDao;
import bookstore.entity.Book;
import bookstore.entity.Genre;
import bookstore.entity.Publisher;

@Service
public class BookStoreService {
	
	@Autowired
	private PublisherDao publisherDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private GenreDao genreDao;

	@Transactional(readOnly = false)
	public PublisherData savePublisher(PublisherData publisherData) {
		Long publisherId = publisherData.getPublisherId();
		Publisher publisher = findOrCreatePublisher(publisherId);
		
		setFieldsInPublisher(publisher, publisherData);
		return new PublisherData(publisherDao.save(publisher));
	}

	private void setFieldsInPublisher(Publisher publisher, PublisherData publisherData) {
		publisher.setName(publisherData.getName());
		publisher.setParentCompany(publisherData.getParentCompany());
		publisher.setCountry(publisherData.getCountry());
		publisher.setRevenue(publisherData.getRevenue());
		}

	private Publisher findOrCreatePublisher(Long publisherId) {
		Publisher publisher;
		
		if(Objects.isNull(publisherId)) {
			publisher = new Publisher();
		}
		else {
			publisher = findPublisherById(publisherId);
		}
		return publisher;
	}

	private Publisher findPublisherById(Long publisherId) {
		return publisherDao.findById(publisherId).orElseThrow(() -> new NoSuchElementException(
				"Publisher with ID = "+ publisherId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<PublisherData> retrieveAllPublishers() {
		// @formatter:off
		return publisherDao.findAll()
				.stream()
				.map(PublisherData::new)
				.toList();
		// @formatter:on
	}
	
	@Transactional(readOnly = true)
	public PublisherData retrievePublisherById(Long publisherId) {
		Publisher publisher = findPublisherById(publisherId);
		return new PublisherData(publisher);
	}
	
	@Transactional(readOnly = false)
	public void deletePublisherById(Long publisherId) {
		Publisher publisher = findPublisherById(publisherId);
		publisherDao.delete(publisher);
		
	}

	@Transactional(readOnly = false)
	public BookData saveBook(Long publisherId, BookData bookData) {
		Publisher publisher = findPublisherById(publisherId);
		
		Set<Genre> genres = genreDao.findAllByNameIn(bookData.getGenres());
		
		Book book = findOrCreateBook(bookData.getBookId());
		
		setBookFields(book, bookData);
		
		book.setPublisher(publisher);
		publisher.getBooks().add(book);
		
		for (Genre genre : genres) {
			genre.getBooks().add(book);
			book.getGenres().add(genre);
		}
		
		Book dbBook = bookDao.save(book);
		return new BookData(dbBook);
	}

	private void setBookFields(Book book, BookData bookData) {
		book.setBookTitle(bookData.getBookTitle());
		book.setBookAuthor(bookData.getBookAuthor());
		book.setYearPublished(bookData.getYearPublished());
		book.setIsbn(bookData.getIsbn());
	}

	private Book findOrCreateBook(Long bookId) {
		Book book;
		if(Objects.isNull(bookId)) {
			book= new Book();
		}
		else {
			book = findBookById(bookId);
		}
		return book;
	}

	private Book findBookById(Long bookId) {
		return bookDao.findById(bookId)
				.orElseThrow(() -> new NoSuchElementException("Book with ID= " + bookId + " does not exist."));
	}
	
	@Transactional(readOnly = true)
	public BookData retrieveBookById(Long publisherId, Long bookId) {
		findPublisherById(publisherId);
		Book book = findBookById(bookId);
		
		if(book.getPublisher().getPublisherId() != publisherId) {
			throw new IllegalStateException(
					"Book with ID = " + bookId + " is not owned by publisher with ID = " + publisherId);
		}
		return new BookData(book);
	}
	
	public void deleteBookById(Long bookId) {
		Book book = findBookById(bookId);
		bookDao.delete(book);
		
	}

	private Genre findGenreById(Long genreId) {
		return genreDao.findById(genreId)
				.orElseThrow(() -> new NoSuchElementException("Genre with ID= " + genreId + " does not exist."));
	}
	
	@Transactional(readOnly = true)
	public List<Genre> retrieveAllGenres() {
		// @formatter:off
		return genreDao.findAll();
	}

	@Transactional(readOnly = false)
	public void deleteGenreById(Long genreId) {
		Genre genre = findGenreById(genreId);
		genreDao.delete(genre);
	}
	
}