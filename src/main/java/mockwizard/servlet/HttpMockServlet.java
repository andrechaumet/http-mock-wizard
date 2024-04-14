package mockwizard.servlet;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mockwizard.exception.MockWizardException;
import mockwizard.model.component.Attribute;
import mockwizard.model.component.HttpRequest;
import mockwizard.model.component.HttpResponse;
import mockwizard.service.MockService;
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

import static java.util.concurrent.Executors.newFixedThreadPool;
import static mockwizard.servlet.extractor.RequestExtractor.extractRequest;

@Component
public class HttpMockServlet implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMockServlet.class);
    private static final Integer POOL_SIZE = 20;
    private final MockService service;
    private final ExecutorService executorService;

    @Autowired
    public HttpMockServlet(final MockService mockService) {
        this.service = mockService;
        this.executorService = newFixedThreadPool(POOL_SIZE);
    }

    @Override
    public void handle(final HttpExchange exchange) {
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
        return service.mock(uri, method, request);
    }

    private void handleResponse(final HttpExchange exchange, final HttpResponse response) throws IOException {
        writeResponseHeaders(exchange, response);
        sendResponseHeaders(exchange, response);
        writeResponseBody(exchange, response);
    }

    private void writeResponseBody(final HttpExchange exchange, final HttpResponse response) throws IOException {
        final String responseBody =
                response.body().stream()
                        .map(Record::toString)
                        .collect(Collectors.joining());
        try (final OutputStream output = exchange.getResponseBody()) {
            output.write(responseBody.getBytes());
        }
    }

    private void sendResponseHeaders(final HttpExchange exchange, final HttpResponse response) throws IOException {
        final int contentLength =
                response.body().stream()
                        .map(Record::toString)
                        .collect(Collectors.joining())
                        .getBytes().length;
        response.headers().forEach(header -> writeHeader(exchange.getResponseHeaders(), header));
        exchange.sendResponseHeaders(response.httpStatus().value(), contentLength);
    }

    private void writeHeader(final Headers headers, final Attribute<?> header) {
        headers.add(header.key(), header.value().toString());
    }

    private void writeResponseHeaders(final HttpExchange exchange, final HttpResponse response) {
        exchange.getResponseHeaders().putAll(writeHeadersAsResponse(response.headers()));
    }

    private Map<String, List<String>> writeHeadersAsResponse(final Set<Attribute<?>> headers) {
        Map<String, List<String>> responseHeaders = new HashMap<>(headers.size());
        for (Attribute<?> header : headers) {
            responseHeaders.put(header.key(), (List<String>) List.of(header.value()));
        }
        return responseHeaders;
    }

}