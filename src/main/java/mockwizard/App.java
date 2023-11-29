package mockwizard;

import mockwizard.config.MockConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class App {

    //TODO: Used for instance managing, fix
    private final MockConfig mockConfig;

    @Autowired
    public App(MockConfig mockConfig) {
        this.mockConfig = mockConfig;
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.run(args);
    }
}