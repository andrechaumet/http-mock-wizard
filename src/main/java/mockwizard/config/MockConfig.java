package mockwizard.config;

import com.sun.net.httpserver.HttpServer;
import mockwizard.request.HttpMockHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@EnableScheduling
public class MockConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockConfig.class);
    private static final Integer PORT = 8088;
    private final HttpMockHandler httpMockHandler;

    @Autowired
    public MockConfig(HttpMockHandler httpMockHandler) {
        this.httpMockHandler = httpMockHandler;
    }

    @Scheduled(initialDelayString = "1000", fixedRate = Long.MAX_VALUE)
    public void init() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", httpMockHandler);
        server.start();
        LOGGER.info("Started mock context in port [{}].", PORT);
    }

}
