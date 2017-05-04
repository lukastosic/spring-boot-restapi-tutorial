package nl.infodation.springtutorial.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.infodation.springtutorial.SpringtutorialApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringtutorialApplication.class)
@WebAppConfiguration
public class HelloControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getHelloTest() {
        try {
            mockMvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(jsonPath("$.message").isNotEmpty())
                    .andExpect(jsonPath("$.message", containsString("GET")));
        } catch (Exception e) {
            throw new RuntimeException("Test failed because " + e.getMessage(), e);
        }
    }

    @Test
    public void postHelloWithoutMessageParameter() {
        try {
            mockMvc.perform(post("/hello")).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            throw new RuntimeException("Test failed because " + e.getMessage(), e);
        }
    }

    @Test
    public void postHelloWithEmptyMessageParameter() {
        try {
            mockMvc.perform(post("/hello?message=")).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            throw new RuntimeException("Test failed because " + e.getMessage(), e);
        }
    }

    @Test
    public void postHelloWithMessageParameter() {
        try {
            mockMvc.perform(post("/hello?message=testhello")).andExpect(status().isOk())
                    .andExpect(jsonPath("$.message", containsString("testhello")));
        } catch (Exception e) {
            throw new RuntimeException("Test failed because " + e.getMessage(), e);
        }
    }

}
