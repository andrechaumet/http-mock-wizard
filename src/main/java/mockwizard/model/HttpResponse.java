package mockwizard.model;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    private String httpStatusCode;
    //TODO: Create "Header" object to store "Boolean required" and Values List<String>
    private Map<String, List<String>> headers;
    private String body;

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
