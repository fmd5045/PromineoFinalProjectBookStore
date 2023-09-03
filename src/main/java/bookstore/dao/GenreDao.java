package bookstore.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.entity.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

	Set<Genre> findAllByNameIn(Set<String> genres);


}
