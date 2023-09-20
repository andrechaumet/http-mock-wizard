package mockwizard.model;

import java.util.List;

public class HttpRequest {
    private List<Header> headers;
    private Body body;

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setBody(String value) {
        this.body = new Body(value);
    }
}
