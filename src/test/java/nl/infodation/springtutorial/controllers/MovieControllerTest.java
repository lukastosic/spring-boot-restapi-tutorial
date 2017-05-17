package nl.infodation.springtutorial.controllers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.infodation.springtutorial.SpringtutorialApplication;
import nl.infodation.springtutorial.models.Movie;
import nl.infodation.springtutorial.repositories.MovieCRUDRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringtutorialApplication.class)
@WebAppConfiguration
public class MovieControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    private static final Logger log = LoggerFactory.getLogger(MovieControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MovieCRUDRepository movieRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private long testMovieID = 0;

    @Before
    public void addTestMovie() {
        log.info(" ### INSERTING TEST MOVIE ### ");
        testMovieID = movieRepository.save(new Movie("TestMovie", 2)).getId();
    }

    @Test
    public void getAllMovies() throws Exception {
        mockMvc.perform(get("/movies")).andExpect(status().isOk());
    }

    @Test
    public void getSingleMovie() throws Exception {
        mockMvc.perform(get("/movies/" + testMovieID)).andExpect(status().isOk());
    }

    @Test
    public void postSingleMovie() throws Exception {
        mockMvc.perform(post("/movies").content(this.json(new Movie("TestMovie", 2))).contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void postSingleMovieWithNoBody() throws Exception {
        mockMvc.perform(post("/movies")).andExpect(status().isBadRequest());
    }

    @Test
    public void postSingleMovieWithBadBody() throws Exception {
        mockMvc.perform(post("/movies").content("{ }").contentType(contentType)).andExpect(status().isBadRequest());
    }

    @After
    public void DeleteTestMovies() {
        log.info(" ### DELETING TEST MOVIE ### ");
        movieRepository.delete(movieRepository.findByName("TestMovie"));
    }

}
