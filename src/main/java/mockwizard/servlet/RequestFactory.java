package mockwizard.servlet;

import com.sun.net.httpserver.HttpExchange;
import mockwizard.model.Header;
import mockwizard.model.HttpRequest;
import mockwizard.model.Param;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestFactory {

    private static final String PARAMS_START = "?";

    public static HttpRequest convertToRequestModel(final HttpExchange exchange) throws IOException {
        final HttpRequest model = new HttpRequest();
        model.setBody(extractBody(exchange.getRequestBody()));
        model.setHeaders(extractHeaders(exchange.getRequestHeaders()));
        model.setParams(extractParams(exchange.getRequestURI().toString()));
        return model;
    }

    private static List<Header> extractHeaders(final Map<String, List<String>> headers) {
        final List<Header> headersFormatted = new LinkedList<>();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            final Header header = new Header();
            header.setKey(entry.getKey());
            header.addValue(entry.getValue());
            headersFormatted.add(header);
        }
        return headersFormatted;
    }

    private static List<Param> extractParams(String path) {
        return (path.contains(PARAMS_START)) ? pathToParams(path) : Collections.emptyList();
    }

    private static List<Param> pathToParams(String path) {
        List<Param> params = new LinkedList<>();
        String[] pairs = path.split("&");
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                params.add(new Param(parts[0], parts[1]));
            }
        }
        return params;
    }


    private static String extractBody(final InputStream body) throws IOException {
        final StringBuilder requestBodyBuilder = new StringBuilder();
        int bytesRead;
        final byte[] buffer = new byte[1024];
        while ((bytesRead = body.read(buffer)) != -1) {
            requestBodyBuilder.append(new String(buffer, 0, bytesRead));
        }
        return requestBodyBuilder.toString();
    }
}
