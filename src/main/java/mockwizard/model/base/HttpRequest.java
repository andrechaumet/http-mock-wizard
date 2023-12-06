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

    private void setParams(List<Param> params) {
        this.params = params;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    private void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public Body getBody() {
        return body;
    }

    private void setBody(Body body) {
        this.body = body;
    }

    private void setBody(String value) {
        this.body = new Body(value);
    }


    public static final class Builder {
        private List<Header> headers;
        private List<Param> params;
        private Body body;

        public static Builder builder() {
            return new Builder();
        }

        public Builder withHeaders(List<Header> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withParams(List<Param> params) {
            this.params = params;
            return this;
        }

        public Builder withBody(Body body) {
            this.body = body;
            return this;
        }

        public Builder withBody(String value) {
            this.body = new Body(value);
            return this;
        }

        public HttpRequest build() {
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setHeaders(headers);
            httpRequest.setParams(params);
            httpRequest.setBody(body);
            return httpRequest;
        }
    }
}
