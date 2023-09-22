package mockwizard.config;

import com.sun.net.httpserver.HttpServer;
import mockwizard.request.HttpMockRequestsHandler;
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
    private final HttpMockRequestsHandler httpMockRequestsHandler;

    @Autowired
    public MockConfig(HttpMockRequestsHandler httpMockRequestsHandler) {
        this.httpMockRequestsHandler = httpMockRequestsHandler;
    }

    //TODO: hotfix solve later
    @Scheduled(initialDelayString = "1000", fixedRate = Long.MAX_VALUE)
    public void init() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", httpMockRequestsHandler);
        server.start();
        LOGGER.info("Started mock context in port [{}].", PORT);
    }

}
