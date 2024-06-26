package mockwizard.servlet;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mockwizard.exception.MockWizardException;
import mockwizard.model.Attribute;
import mockwizard.model.HttpRequest;
import mockwizard.model.HttpResponse;
import mockwizard.service.MockProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static mockwizard.servlet.RequestExtractor.extractRequest;

@Component
public class HttpMockServlet implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMockServlet.class);
    private final ExecutorService executorService;
    private final MockProvider mockProvider;

    @Autowired
    public HttpMockServlet(final MockProvider mockProvider, final ExecutorService executorService) {
        this.mockProvider = mockProvider;
        this.executorService = executorService;
    }

    @Override
    public void handle(final HttpExchange exchange) {
        LOGGER.info("Mocking method [{}] with URI [{}]", exchange.getRequestMethod(), exchange.getRequestURI());
        executorService.submit(() -> handleAsync(exchange));
    }

    private void handleAsync(final HttpExchange exchange) {
        try (exchange) {
            handleResponse(exchange, handleRequest(exchange));
        } catch (final MockWizardException e) {
            LOGGER.error("Error [{}] while handling mock request [{}].", e.getCode(), e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("Generic error while handling mock request [{}].", e.getMessage());
        }
    }

    private HttpResponse handleRequest(final HttpExchange exchange) {
        final HttpRequest request = extractRequest(exchange);
        final String method = exchange.getRequestMethod();
        final String uri = exchange.getRequestURI().toString();
        return mockProvider.mock(uri, method, request);
    }

    private void handleResponse(final HttpExchange exchange, final HttpResponse response) throws IOException {
        writeResponseHeaders(exchange, response);
        sendResponseHeaders(exchange, response);
        writeResponseBody(exchange, response);
    }

    private void writeResponseBody(final HttpExchange exchange, final HttpResponse response) throws IOException {
        final String responseBody =
                response.body().stream()
                        .map(Attribute::valueAsString)
                        .collect(Collectors.joining());
        try (final OutputStream output = exchange.getResponseBody()) {
            output.write(responseBody.getBytes());
        }
    }

    private void sendResponseHeaders(final HttpExchange exchange, final HttpResponse response) throws IOException {
        final int contentLength =
                response.body().stream()
                        .map(Attribute::valueAsString)
                        .collect(Collectors.joining())
                        .getBytes()
                        .length;
        response.headers().forEach(header -> writeHeader(exchange.getResponseHeaders(), header));
        exchange.sendResponseHeaders(response.httpStatus().value(), contentLength);
    }

    private void writeHeader(final Headers headers, final Attribute<?> header) {
        headers.add(header.key(), header.valueAsString());
    }

    private void writeResponseHeaders(final HttpExchange exchange, final HttpResponse response) {
        exchange.getResponseHeaders().putAll(writeHeadersAsResponse(response.headers()));
    }

    private Map<String, List<String>> writeHeadersAsResponse(final Set<Attribute<?>> headers) {
        Map<String, List<String>> responseHeaders = new HashMap<>(headers.size());
        headers.forEach(header -> responseHeaders.put(header.key(), List.of(header.valueAsString())));
        return responseHeaders;
    }
}