package mockwizard;

import mockwizard.config.ContextConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    private final ContextConfig contextConfig;

    @Autowired
    public App(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
        this.contextConfig.init();
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.run(args);
    }
}