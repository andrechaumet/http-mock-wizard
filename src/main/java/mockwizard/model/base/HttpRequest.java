package mockwizard.model.base;

import mockwizard.model.component.Body;
import mockwizard.model.component.Header;
import mockwizard.model.component.Param;

import java.util.List;

public class HttpRequest {
    private List<Header> headers;
    private List<Param> params;
    private Body body;

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

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