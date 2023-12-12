package mockwizard.servlet;

import com.sun.net.httpserver.HttpExchange;
import mockwizard.exception.MockWizardException;
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

import static mockwizard.servlet.RequestExtractor.extractRequest;

@Component
public class HttpMockServlet implements com.sun.net.httpserver.HttpHandler {

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
        executorService.submit(() -> handleAsync(exchange));
    }

    private void handleAsync(final HttpExchange exchange) {
        try {
            handleResponse(exchange, handleRequest(exchange));
        } catch (final MockWizardException e) {
            LOGGER.error("Error while handling mock request [{}].", e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("Generic error while handling mock request [{}].", e.getMessage());
        } finally {
            exchange.close();
        }
    }

    private HttpResponse handleRequest(final HttpExchange exchange) {
        final HttpRequest request = extractRequest(exchange);
        final String method = exchange.getRequestMethod();
        final String uri = exchange.getRequestURI().toString();
        return service.mock(uri, method, request);
    }

    private void handleResponse(final HttpExchange exchange, final HttpResponse response) throws IOException {
        writeResponseBody(exchange, response);
        setResponseHeaders(exchange, response);
        sendResponseHeaders(exchange, response);
    }

    private void writeResponseBody(HttpExchange exchange, HttpResponse response) throws IOException {
        final String responseBody = response.getBody();
        try (final OutputStream output = exchange.getResponseBody()) {
            output.write(responseBody.getBytes());
        }
    }

    private void sendResponseHeaders(HttpExchange exchange, HttpResponse response) throws IOException {
        final int contentLength = response.getBody().getBytes().length;
        exchange.sendResponseHeaders(response.getHttpStatusCode(), contentLength);
    }

    private void setResponseHeaders(HttpExchange exchange, HttpResponse response) {
        exchange.getResponseHeaders().putAll(response.getHeadersAsResponse());
    }
}