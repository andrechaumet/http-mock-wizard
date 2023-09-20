package mockwizard.request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mockwizard.model.*;
import mockwizard.service.impl.MockServiceImpl;
import mockwizard.service.impl.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpRequestHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final RequestValidator validator = new RequestValidator();

    private final MockServiceImpl service = new MockServiceImpl();

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
            //EXTRACTS VALUES
            final HttpRequest request = convertToModel(exchange);
            final String httpMethod = exchange.getRequestMethod();
            final String path = exchange.getRequestURI().toString();
            //IF VALUE, RETURNS RESPONSE
            final MockFile mockFile = service.mock(path, httpMethod);
            if (!validator.isValid(request, mockFile.getKey())) {
                LOGGER.info("HTTP Request does not achieve required parameters.");
                throw new IllegalArgumentException();
            }

            OutputStream output = exchange.getResponseBody();

            String responseBody = mockFile.getValue().getBody();
            exchange.getResponseHeaders().putAll(mockFile.getValue().getHeaders());
            exchange.sendResponseHeaders(Integer.parseInt(mockFile.getValue().getHttpStatusCode()), responseBody.getBytes().length);
            output.write(responseBody.getBytes());
            output.close();
            exchange.close();

        } catch (IOException e) {
            exchange.sendResponseHeaders(500, 0);
            exchange.close();
        }
    }

    private HttpRequest convertToModel(final HttpExchange exchange) throws IOException {
        final Map<String, List<String>> headers = exchange.getRequestHeaders();
        final String body = extractBody(exchange.getRequestBody());
        final HttpRequest model = new HttpRequest();
        model.setBody(new Body(body));
        model.setHeaders(formatHeaders(headers));
        return model;
    }

    private List<Header> formatHeaders(final Map<String, List<String>> headers) {
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