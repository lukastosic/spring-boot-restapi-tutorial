package nl.infodation.springtutorial.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.infodation.springtutorial.models.Movie;
import nl.infodation.springtutorial.repositories.MovieCRUDRepository;

@Controller
@RequestMapping(path = "/movies")
@ResponseBody
@EnableAutoConfiguration
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieCRUDRepository movieRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Movie postNewMovie(@RequestBody(required = true) Movie movie) {
        return movieRepository.save(movie);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Movie getSingleMovieById(@PathVariable Long id) {
        return movieRepository.findOne(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteSingleMovieById(@PathVariable Long id) {
        try {
            movieRepository.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    void handleWrongRequestTypeException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Request body is in the wrong format");
    }

}
