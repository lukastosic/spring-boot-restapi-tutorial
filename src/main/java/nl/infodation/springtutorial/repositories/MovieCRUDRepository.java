package nl.infodation.springtutorial.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.infodation.springtutorial.models.Movie;

public interface MovieCRUDRepository extends CrudRepository<Movie, Long> {

    Movie findOneByName(String name);

    List<Movie> findByName(String name);

    List<Movie> findByNameContains(String name);
}
