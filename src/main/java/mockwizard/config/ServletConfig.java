package mockwizard.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Configuration
public class ServletConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletConfig.class);
    private static final Integer POOL_SIZE = 20;

    @Bean
    public ExecutorService executorService() {
        LOGGER.info("HttpMockServlet initialized with a thread pool size of [{}].", POOL_SIZE);
        return newFixedThreadPool(POOL_SIZE);
    }
}