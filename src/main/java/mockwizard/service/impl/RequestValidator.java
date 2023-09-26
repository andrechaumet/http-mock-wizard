package mockwizard.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import mockwizard.model.Header;
import mockwizard.model.HttpRequest;
import mockwizard.model.Param;

import java.util.List;

public class RequestValidator {
    private static final JsonParser PARSER = new JsonParser();

    public static boolean validBody(HttpRequest sent, HttpRequest found) {
        if (found.getBody() == null) return true;
        final JsonElement sentBody = PARSER.parse(sent.getBody().getValue());
        final JsonElement foundBody = PARSER.parse(found.getBody().getValue());
        return sentBody.equals(foundBody);
    }

    public static boolean validHeaders(HttpRequest sent, HttpRequest found) {
        final List<Header> headers = found.getHeaders();
        if (headers == null) return true;
        return found.getHeaders().stream()
                .filter(Header::isRequired)
                .allMatch(header -> sent.getHeaders().contains(header));
    }

    public static boolean validParams(HttpRequest sent, HttpRequest found) {
        List<Param> params = found.getParams();
        if (params == null) return true;
        return params.stream()
                .filter(Param::isRequired)
                .allMatch(param -> sent.getParams().contains(param));
    }
}
