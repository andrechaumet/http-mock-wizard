package mockwizard;

import mockwizard.config.MockConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    private final MockConfig mockConfig;

    @Autowired
    public App(MockConfig mockConfig) {
        this.mockConfig = mockConfig;
        this.mockConfig.init();
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.run(args);
    }
}