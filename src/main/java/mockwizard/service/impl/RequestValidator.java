package mockwizard.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import mockwizard.model.Header;
import mockwizard.model.HttpRequest;
import mockwizard.model.Param;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestValidator {
    private static final JsonParser PARSER = new JsonParser();

    public static boolean validBody(HttpRequest sent, HttpRequest found) {
        if (found.getBody() == null) return false;
        final JsonElement sentBody = PARSER.parse(sent.getBody().getValue());
        final JsonElement foundBody = PARSER.parse(found.getBody().getValue());
        return sentBody.equals(foundBody);
    }

    public static boolean validHeaders(HttpRequest sent, HttpRequest found) {
        if (found.getHeaders() != null) {
            Map<String, List<String>> headersSent = sent.getHeaders()
                    .stream()
                    .collect(Collectors
                            .toMap(
                                    header -> header.getKey().toLowerCase(),
                                    header -> header.getValues()
                                            .stream()
                                            .map(String::toLowerCase)
                                            .collect(Collectors.toList())
                            )
                    );
            for (Header header : found.getHeaders()) {
                if (header.isRequired()) {
                    final String requiredKey = header.getKey();
                    final List<String> requiredValues = header.getValues();
                    if (!headersSent.get(requiredKey.toLowerCase()).containsAll(requiredValues.stream().map(String::toLowerCase).collect(Collectors.toList()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean validParams(HttpRequest sent, HttpRequest found) {
        List<Param> params = found.getParams();
        if (params != null) {
            return params.stream()
                    .filter(Param::isRequired)
                    .allMatch(param -> sent.getParams().contains(param));
        } else {
            return false;
        }
    }
}
