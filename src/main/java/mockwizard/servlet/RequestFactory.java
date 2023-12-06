package mockwizard.servlet;

import com.sun.net.httpserver.HttpExchange;
import mockwizard.model.component.Header;
import mockwizard.model.base.HttpRequest;
import mockwizard.model.component.Param;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static mockwizard.model.base.HttpRequest.Builder.builder;

public class RequestFactory {

    private static final String PARAMS_START = "?";
    private static final String PARAM_PLUS = "&";
    private static final String PARAM_VALUE = "=";
    private static final Integer KEY_POSITION = 0;
    private static final Integer VALUE_POSITION = 1;
    private static final Integer EXPECTED_KEY_VALUE_LENGTH = 2;
    private static final Integer BUFFER_SIZE = 1024;
    private static final Integer OFFSET = 0;
    private static final Integer NO_MORE_DATA = -1;

    private RequestFactory() {
    }

    public static HttpRequest convertToRequestModel(final HttpExchange exchange) throws IOException {
        return builder()
                .withBody(extractBody(exchange.getRequestBody()))
                .withHeaders(extractHeaders(exchange.getRequestHeaders()))
                .withParams(extractParams(exchange.getRequestURI()))
                .build();
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

    private static List<Param> extractParams(final URI uri) {
        final String path = uri.toString();
        return (path.contains(PARAMS_START)) ? pathToParams(path) : emptyList();
    }

    private static List<Param> pathToParams(final String path) {
        final List<Param> params = new LinkedList<>();
        final String[] pairs = path.split(PARAM_PLUS);
        for (String pair : pairs) {
            final String[] parts = pair.split(PARAM_VALUE);
            if (containsValue(parts)) {
                params.add(new Param(parts[KEY_POSITION], parts[VALUE_POSITION]));
            }
        }
        return params;
    }

    private static boolean containsValue(String[] part) {
        return part.length == EXPECTED_KEY_VALUE_LENGTH;
    }

    private static String extractBody(final InputStream body) throws IOException {
        final StringBuilder requestBodyBuilder = new StringBuilder();
        int bytesRead;
        final byte[] buffer = new byte[BUFFER_SIZE];
        while ((bytesRead = body.read(buffer)) != NO_MORE_DATA) {
            requestBodyBuilder.append(new String(buffer, OFFSET, bytesRead));
        }
        return requestBodyBuilder.toString();
    }
}
