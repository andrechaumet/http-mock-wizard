package mockwizard.service.impl;

import mockwizard.model.Header;
import mockwizard.model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestComparator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestComparator.class);


    public boolean compare(HttpRequest requested, HttpRequest found) {
        return compareBodies(requested, found) && compareHeaders(requested, found);
    }

    private boolean compareBodies(HttpRequest request, HttpRequest found) {
        if (found.getBody().isRequired()) {
            final boolean value = found.getBody().getValue().equals(request.getBody().getValue());
            if (!value) {
                LOGGER.info("BODIES DONT MATCH");
            }
        }
        return true;
    }

    private boolean compareHeaders(HttpRequest requested, HttpRequest found) {
        if (found.getHeaders() != null) {
            for (Header header : found.getHeaders()) {
                if (header.isRequired()) {
                    if (!requested.getHeaders().contains(header)) {
                        LOGGER.info("HEADERS DONT MATCH");
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
