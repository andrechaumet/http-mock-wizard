package mockwizard.request;

import com.sun.net.httpserver.HttpExchange;
import mockwizard.exception.HttpMockWizardException;
import mockwizard.model.*;
import mockwizard.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static mockwizard.request.RequestFactory.convertToRequestModel;

@Component
public class HttpMockRequestsHandler implements com.sun.net.httpserver.HttpHandler {

    //TODO: Log
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMockRequestsHandler.class);

    private final MockService service;

    @Autowired
    public HttpMockRequestsHandler(MockService mockService) {
        this.service = mockService;
    }

    @Override
    public void handle(HttpExchange exchange) {
        new Thread(() -> {
            try {
                handleAsync(exchange);
                //TODO: exception handling, refactor, create metrics & save error data as file
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    //TODO: Store in the mock file how much delay I want the response to return.
    private void handleAsync(final HttpExchange exchange) throws IOException, InterruptedException {
        try {
            handleResponse(exchange, handleRequest(exchange));
        } catch (HttpMockWizardException e) {
            exchange.sendResponseHeaders(e.getCode(), e.getMessage().length());
            exchange.getResponseBody().write(e.getMessage().getBytes());
        } catch (IOException e) {
            exchange.sendResponseHeaders(500, 0);
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
        exchange.getResponseHeaders().putAll(response.getHeaders());
    }
}