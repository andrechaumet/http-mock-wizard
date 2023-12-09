package mockwizard.config;

import com.sun.net.httpserver.HttpServer;
import mockwizard.servlet.HttpMockServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@EnableScheduling
public class MockConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockConfig.class);

    private static final Integer PORT = 8088;
    private static final Integer DEFAULT_BACKLOG = -1;
    private static final Integer SHUTTING_DELAY = 0;
    private static final String BASE_PATH = "/";

    private final HttpMockServlet httpMockServlet;
    private final HttpServer server;

    @Autowired
    public MockConfig(HttpMockServlet httpMockServlet) throws IOException {
        this.httpMockServlet = httpMockServlet;
        this.server = HttpServer.create(new InetSocketAddress(PORT), DEFAULT_BACKLOG);
    }

    public void init() {
        server.createContext(BASE_PATH, httpMockServlet);
        server.start();
        LOGGER.info("Started mock context at port [{}].", PORT);
    }

    @PreDestroy
    public void destroy() {
        if (server != null) {
            server.stop(SHUTTING_DELAY);
            LOGGER.info("Stopped mock context at port [{}].", PORT);
        }
    }
}
