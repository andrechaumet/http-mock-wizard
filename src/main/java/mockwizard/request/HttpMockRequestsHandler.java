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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        final HttpRequest request = convertToModel(exchange);
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

    private HttpRequest convertToModel(final HttpExchange exchange) throws IOException {
        final HttpRequest model = new HttpRequest();
        model.setBody(extractBody(exchange.getRequestBody()));
        model.setHeaders(extractHeaders(exchange.getRequestHeaders()));
        model.setParams(extractParams(exchange.getRequestURI().toString()));
        return model;
    }

    private List<Header> extractHeaders(final Map<String, List<String>> headers) {
        final List<Header> headersFormatted = new LinkedList<>();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            final Header header = new Header();
            header.setKey(entry.getKey());
            header.addValue(entry.getValue());
            headersFormatted.add(header);
        }
        return headersFormatted;
    }

    //TODO: create sub methods, refactor
    private List<Param> extractParams(String path) {
        List<Param> params;
        if (path.contains("?")) {
            params = new LinkedList<>();
            String[] pairs = path.split("[&$]");
            for (String pair : pairs) {
                String[] parts = pair.split("=");
                if (parts.length == 2) {
                    params.add(new Param(parts[0], parts[1]));
                }
            }
            return params;
        } else {
            return Collections.emptyList();
        }
    }

    private String extractBody(final InputStream body) throws IOException {
        final StringBuilder requestBodyBuilder = new StringBuilder();
        int bytesRead;
        final byte[] buffer = new byte[1024];
        while ((bytesRead = body.read(buffer)) != -1) {
            requestBodyBuilder.append(new String(buffer, 0, bytesRead));
        }
        return requestBodyBuilder.toString();
    }
}