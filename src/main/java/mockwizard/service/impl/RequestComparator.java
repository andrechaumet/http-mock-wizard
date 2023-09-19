package mockwizard.service.impl;

import mockwizard.model.Header;
import mockwizard.model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestComparator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestComparator.class);


    public boolean compare(HttpRequest sent, HttpRequest found) {
        return compareBodies(sent, found) && compareHeaders(sent, found);
    }

    private boolean compareBodies(HttpRequest sent, HttpRequest found) {
        if (found.getBody() != null) {
            if (found.getBody().isRequired()) {
                if (sent.getBody() == null) {
                    return false;
                }
                System.out.println("BODY SAVED:");
                System.out.println(found.getBody().getValue());
                System.out.println("BODY SENT:");
                //TODO: Dont look at me, i know its bad, trust me i know
                System.out.println(sent.getBody().getValue().replaceAll(" ", "").replaceAll("\r\n", " ").replaceAll(" ", ""));

                final boolean value = found.getBody()
                        .getValue()
                        .equals(sent.getBody().getValue()
                                .replaceAll(" ", "")
                                .replaceAll("\r\n", " ")
                                .replaceAll(" ", "")
                        );
                if (!value) {
                    LOGGER.info("BODIES DONT MATCH");
                }
            }
        }
        return true;
    }

    //TODO:
    private boolean compareHeaders(HttpRequest sent, HttpRequest found) {
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
                if(!headersSent.get(requiredKey.toLowerCase()).containsAll(requiredValues.stream().map(String::toLowerCase).collect(Collectors.toList()))) {
                    return false;
                }
            }
        }
        return true;
    }
}
