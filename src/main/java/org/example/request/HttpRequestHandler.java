package org.example.request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.HttpRequest;
import org.example.model.HttpResponse;
import org.example.service.impl.MockServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class HttpRequestHandler implements HttpHandler {

    private final MockServiceImpl service = new MockServiceImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //EXTRACTS VALUES
            final String path = exchange.getRequestURI().toString();
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
                exchange.sendResponseHeaders(Integer.parseInt(response.getHttpStatusCode()), responseBody.getBytes().length);
                os.write(responseBody.getBytes());
                os.close();
                exchange.getResponseHeaders().add("asd", "asd");
                exchange.close();
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("------ERROR------");
            exchange.sendResponseHeaders(500, 0);
            exchange.close();
        }
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