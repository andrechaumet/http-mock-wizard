package org.example.mocks.files;

import java.util.Map;

public class HttpRequest {
    private String uri;
    private Map<String,String> requiredHeaders;
    private String requiredBody;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getRequiredHeaders() {
        return requiredHeaders;
    }

    public void setRequiredHeaders(Map<String, String> requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
    }

    public String getRequiredBody() {
        return requiredBody;
    }

    public void setRequiredBody(String requiredBody) {
        this.requiredBody = requiredBody;
    }
}
