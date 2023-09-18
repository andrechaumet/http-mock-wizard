package mockwizard.service.impl;

import mockwizard.model.Header;
import mockwizard.model.HttpRequest;

public class RequestComparator {

    public boolean compare(HttpRequest requested, HttpRequest found) {
        return compareBodies(requested,found) && compareHeaders(requested, found);
    }

    private boolean compareBodies(HttpRequest request, HttpRequest found) {
        if(found.getBody().isRequired()) {
            return found.getBody().getValue().equals(request.getBody().getValue());
        }
        return true;
    }

    private boolean compareHeaders(HttpRequest requested, HttpRequest found) {
        for(Header header : found.getHeaders()) {
            if(header.isRequired()) {
                if(!requested.getHeaders().contains(header)) {
                    return false;
                }
            }
        }
        return true;
    }

}
