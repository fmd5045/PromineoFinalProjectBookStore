package bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.entity.Book;

public interface BookDao extends JpaRepository<Book, Long> {

}
