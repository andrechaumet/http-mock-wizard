package mockwizard;

import com.sun.net.httpserver.HttpServer;
import mockwizard.request.HttpRequestHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.InetSocketAddress;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8087), 0);
        server.createContext("/", new HttpRequestHandler());
        server.start();

        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.run(args);
    }
}