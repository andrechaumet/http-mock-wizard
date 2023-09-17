package org.example.mocks.files;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    private String httpStatusCode;
    private Map<String, List<String>> headers;
    private String body;

    public String httpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String body() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }
}
