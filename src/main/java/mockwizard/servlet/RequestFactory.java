package mockwizard.servlet;

import com.sun.net.httpserver.HttpExchange;
import mockwizard.model.component.Header;
import mockwizard.model.base.HttpRequest;
import mockwizard.model.component.Param;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

public class RequestFactory {

    private static final String PARAMS_START = "?";
    private static final String PARAM_PLUS = "&";
    private static final String PARAM_VALUE = "=";
    private static final Integer KEY_POSITION = 0;
    private static final Integer VALUE_POSITION = 1;
    private static final Integer EXPECTED_KEY_VALUE_LENGTH = 2;

    private RequestFactory() {
    }

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

    private static List<Param> extractParams(final String path) {
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
        final byte[] buffer = new byte[1024];
        while ((bytesRead = body.read(buffer)) != -1) {
            requestBodyBuilder.append(new String(buffer, 0, bytesRead));
        }
        return requestBodyBuilder.toString();
    }
}
