package mockwizard.servlet;

import com.sun.net.httpserver.HttpExchange;
import mockwizard.model.base.HttpRequest;
import mockwizard.model.base.HttpResponse;
import mockwizard.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static mockwizard.servlet.RequestFactory.convertToRequestModel;

@Component
public class HttpMockServlet implements com.sun.net.httpserver.HttpHandler {
    //TODO: Log
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMockServlet.class);
    private static final Integer POOL_SIZE = 20;
    private final MockService service;
    private final ExecutorService executorService;


    @Autowired
    public HttpMockServlet(MockService mockService) {
        this.service = mockService;
        this.executorService = Executors.newFixedThreadPool(POOL_SIZE);
    }


    @Override
    public void handle(final HttpExchange exchange) {
        executorService.submit(() -> {
            handleAsync(exchange);
        });
    }

    //TODO: Store in the mock file how much delay I want the response to return.
    private void handleAsync(final HttpExchange exchange) {
        try {
            handleResponse(exchange, handleRequest(exchange));
        } catch (final Exception e) {
            //TODO: create class/factory for this
            LOGGER.error("Generic error while handling mock request [{}].", e.getMessage());
        } finally {
            exchange.close();
        }
    }

    private HttpResponse handleRequest(final HttpExchange exchange) throws IOException {
        final HttpRequest request = convertToRequestModel(exchange);
        final String httpMethod = exchange.getRequestMethod();
        final String path = exchange.getRequestURI().toString();
        return service.mock(path, httpMethod, request);
    }

    private void handleResponse(final HttpExchange exchange, final HttpResponse response) throws IOException {
        final String responseBody = response.getBody();
        try (final OutputStream output = exchange.getResponseBody()) {
            output.write(responseBody.getBytes());
        }
        exchange.sendResponseHeaders(Integer.parseInt(response.getHttpStatusCode()), responseBody.getBytes().length);
        exchange.getResponseHeaders().putAll(response.getHeadersAsResponse());
    }
}