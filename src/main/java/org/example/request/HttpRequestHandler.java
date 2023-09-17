package org.example.request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.mocks.files.HttpRequest;
import org.example.mocks.files.HttpResponse;
import org.example.service.impl.MockServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class HttpRequestHandler implements HttpHandler {

    private MockServiceImpl service = new MockServiceImpl();

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
            request.setPath(path);
            request.setHttpMethod(httpMethod);
            request.setRequiredBody(body);
            request.setRequiredHeaders(headers);

            //IF VALUE, RETURNS RESPONSE
            final HttpResponse test = service.test(request);


        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("------ERROR------");
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