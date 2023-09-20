package mockwizard.request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mockwizard.model.*;
import mockwizard.service.MockService;
import mockwizard.service.impl.MockServiceImpl;
import mockwizard.service.impl.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class HttpRequestHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final MockService service;

    @Autowired
    public HttpRequestHandler(MockService mockService) {
        this.service = mockService;
    }

    @Override
    public void handle(HttpExchange exchange) {
        Thread newThread = new Thread(() -> {
            try {
                handleAsync(exchange);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        newThread.start();
    }

    //TODO: Store in the mock file how much delay I want the response to return.
    private void handleAsync(final HttpExchange exchange) throws IOException, InterruptedException {
        try {
            final HttpRequest request = convertToModel(exchange);
            final String httpMethod = exchange.getRequestMethod();
            final String path = exchange.getRequestURI().toString();


            final HttpResponse response = service.mock(path, httpMethod, request);

            OutputStream output = exchange.getResponseBody();

            String responseBody = response.getBody();
            exchange.getResponseHeaders().putAll(response.getHeaders());
            exchange.sendResponseHeaders(Integer.parseInt(response.getHttpStatusCode()), responseBody.getBytes().length);
            output.write(responseBody.getBytes());
            output.close();
            exchange.close();

        } catch (IOException e) {
            exchange.sendResponseHeaders(500, 0);
            exchange.close();
        }
    }

    private HttpRequest convertToModel(final HttpExchange exchange) throws IOException {
        final HttpRequest model = new HttpRequest();
        model.setBody(extractBody(exchange.getRequestBody()));
        model.setHeaders(extractHeaders(exchange.getRequestHeaders()));
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