package nl.infodation.springtutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringtutorialApplication {

    public SpringtutorialApplication() {
        // Method needed for JAVA Sonar code standards
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringtutorialApplication.class, args);
    }
}
