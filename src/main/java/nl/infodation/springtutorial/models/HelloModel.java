package nl.infodation.springtutorial.models;

public class HelloModel {

    private String message;

    public HelloModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
