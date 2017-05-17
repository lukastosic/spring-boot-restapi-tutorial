package nl.infodation.springtutorial.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private int rating;

    public Movie() {
        // Needed for Spring JPA Hibernate usage
    }

    public Movie(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id of the Movie to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the Name of the Movie to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating
     *            the Rating of the Movie to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
