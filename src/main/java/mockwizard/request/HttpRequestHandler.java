package mockwizard.request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mockwizard.model.Header;
import mockwizard.model.HttpRequest;
import mockwizard.model.HttpResponse;
import mockwizard.service.impl.MockServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequestHandler implements HttpHandler {

    private final MockServiceImpl service = new MockServiceImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Thread newThread = new Thread(() -> {
            try {
                handleAsync(exchange);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        newThread.start();        /*try {
            //EXTRACTS VALUES
            final String path = formatPath(exchange.getRequestURI().toString());
            final String httpMethod = exchange.getRequestMethod();
            final String body = extractBody(exchange.getRequestBody());
            final Map<String, List<String>> headers = exchange.getRequestHeaders();
            //CREATES REQUEST
            final HttpRequest request = new HttpRequest();
            request.setHttpMethod(httpMethod);
            request.setRequiredBody(body);
            request.setRequiredHeaders(headers);
            //IF VALUE, RETURNS RESPONSE
            final HttpResponse response = service.mock(path, request);
            if (response == null) {
                exchange.sendResponseHeaders(404, 0);
                exchange.close();
            } else {
                OutputStream os = exchange.getResponseBody();
                String responseBody = response.getBody();
                for (Map<String, List<String> dsa : response.getHeaders().) {

                }
                for (Map.Entry<String, List<String>> asd : response.getHeaders().entrySet()) {
                    exchange.getResponseHeaders().add("asd", "asd");
                }
                exchange.sendResponseHeaders(Integer.parseInt(response.getHttpStatusCode()), responseBody.getBytes().length);
                os.write(responseBody.getBytes());
                os.close();
                exchange.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("------ERROR------");
            exchange.sendResponseHeaders(500, 0);
            exchange.close();
        }*/
    }

    //TODO: Store in the mock files how much delay I want the response to return.
    private void handleAsync(HttpExchange exchange) throws IOException, InterruptedException {
        try {
            //EXTRACTS VALUES
            final String path = formatPath(exchange.getRequestURI().toString());
            final String httpMethod = exchange.getRequestMethod();
            final String body = extractBody(exchange.getRequestBody());
            final Map<String, List<String>> headers = exchange.getRequestHeaders();
            //CREATES REQUEST
            final HttpRequest request = convertToModel(exchange);
/*
            request.setHttpMethod(httpMethod);
*/
            request.setRequiredBody(body);
            request.setHeaders(headers);
            //IF VALUE, RETURNS RESPONSE
            final HttpResponse response = service.mock(path, request, httpMethod);
            if (response == null) {
                exchange.sendResponseHeaders(404, 0);
                exchange.close();
            } else {
                OutputStream os = exchange.getResponseBody();
                String responseBody = response.getBody();
                exchange.getResponseHeaders().add("asd", "asd");
                exchange.sendResponseHeaders(Integer.parseInt(response.getHttpStatusCode()), responseBody.getBytes().length);
                os.write(responseBody.getBytes());
                os.close();
                exchange.close();
            }


        } catch (IOException e) {
            exchange.sendResponseHeaders(500, 0);
            exchange.close();
        } finally {
        }

    }

    private HttpRequest convertToModel(HttpExchange exchange) throws IOException {
        final String body = extractBody(exchange.getRequestBody());
        final Map<String, List<String>> headers = exchange.getRequestHeaders();
        final HttpRequest model = new HttpRequest();
        model.setRequiredBody(body);

        model.setHeaders();
    }

    private String formatPath(String path) {
        return path.replaceFirst("/", "");
    }

    private String extractBody(final InputStream body) throws IOException {
        StringBuilder requestBodyBuilder = new StringBuilder();
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = body.read(buffer)) != -1) {
            requestBodyBuilder.append(new String(buffer, 0, bytesRead));
        }
        return requestBodyBuilder.toString();
    }
}