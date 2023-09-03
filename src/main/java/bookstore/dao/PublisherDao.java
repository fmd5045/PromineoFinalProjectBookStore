package bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.entity.Publisher;

public interface PublisherDao extends JpaRepository<Publisher, Long> {

}
