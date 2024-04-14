package mockwizard.servlet;

import com.sun.net.httpserver.HttpExchange;
import mockwizard.exception.MockWizardException;
import mockwizard.model.Attribute;
import mockwizard.model.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;
import static mockwizard.exception.DetailedException.INVALID_REQUEST_BODY;

public class RequestExtractor {

    private static final String PARAMS_START = "?";
    private static final String PARAM_PLUS = "&";
    private static final String PARAM_VALUE = "=";
    private static final String ATTRIBUTE_DELIMITER = ":";

    private static final Integer KEY_POSITION = 0;
    private static final Integer VALUE_POSITION = 1;

    private static final Integer BUFFER_SIZE = 1024;
    private static final Integer OFFSET = 0;
    private static final Integer NO_MORE_DATA = -1;

    private RequestExtractor() {
    }

    public static HttpRequest extractRequest(final HttpExchange exchange) {
        return new HttpRequest(
                extractHeaders(exchange.getRequestHeaders()),
                extractParams(exchange.getRequestURI()),
                extractBody(exchange.getRequestBody())
        );
    }

    private static Set<Attribute<?>> extractHeaders(final Map<String, List<String>> headers) {
        final Set<Attribute<?>> headersFormatted = new HashSet<>();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            headersFormatted.add(new Attribute<>(entry.getKey(), entry.getValue()));
        }
        return headersFormatted;
    }

    private static Set<Attribute<?>> extractParams(final URI uri) {
        final String path = uri.toString();
        return (path.contains(PARAMS_START)) ? pathToParams(path) : emptySet();
    }

    private static Set<Attribute<?>> pathToParams(final String path) {
        final Set<Attribute<?>> params = new HashSet<>();
        for (String pair : path.split(PARAM_PLUS)) {
            final String[] parts = pair.split(PARAM_VALUE);
            params.add(new Attribute<>(parts[KEY_POSITION], parts[VALUE_POSITION]));
        }
        return params;
    }

    private static Set<Attribute<?>> extractBody(final InputStream input) {
        try {
            return createBody(getJson(input));
        } catch (Exception e) {
            throw new MockWizardException(INVALID_REQUEST_BODY);
        }
    }

    private static String getJson(final InputStream body) throws IOException {
        final StringBuilder bodyBuilder = new StringBuilder();
        final byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = body.read(buffer)) != NO_MORE_DATA) {
            bodyBuilder.append(new String(buffer, OFFSET, bytesRead));
        }
        return bodyBuilder.toString();
    }

    private static Set<Attribute<?>> createBody(String jsonBytes) {
        Set<Attribute<?>> attributes = new HashSet<>();
        String[] keyValuePairs = cleanKeyValuePairs(jsonBytes);
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(ATTRIBUTE_DELIMITER);
            attributes.add(new Attribute<>(cleanEntry(entry[KEY_POSITION]), cleanEntry(entry[VALUE_POSITION])));
        }
        return attributes;
    }

    private static String[] cleanKeyValuePairs(String json) {
        return json.trim()
                .replace("{", "")
                .replace("}", "")
                .split(",");
    }

    private static String cleanEntry(String pair) {
        return pair.trim().replace("\"", "");
    }
}
