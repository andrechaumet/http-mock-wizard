package mockwizard.service.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import mockwizard.model.component.Header;
import mockwizard.model.base.HttpRequest;
import mockwizard.model.component.Param;

import java.util.List;

public class RequestValidator {
    private static final JsonParser PARSER = new JsonParser();

    private RequestValidator() {

    }

    public static boolean validBody(final HttpRequest sent, final HttpRequest found) {
        if (found.getBody() == null || !found.getBody().isRequired()) return true;
        final JsonElement sentBody = PARSER.parse(sent.getBody().getValue());
        final JsonElement foundBody = PARSER.parse(found.getBody().getValue());
        return sentBody.equals(foundBody);
    }

    public static boolean validHeaders(final HttpRequest sent, final HttpRequest found) {
        final List<Header> headers = found.getHeaders();
        if (headers == null) return true;
        return found.getHeaders().stream()
                .filter(Header::isRequired)
                .allMatch(header -> sent.getHeaders().contains(header));
    }

    public static boolean validParams(final HttpRequest sent, final HttpRequest found) {
        final List<Param> params = found.getParams();
        if (params == null) return true;
        return params.stream()
                .filter(Param::isRequired)
                .allMatch(param -> sent.getParams().contains(param));
    }
}
