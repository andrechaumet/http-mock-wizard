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

    public List<Header> getHeaders() {
        return headers;
    }

    public Body getBody() {
        return body;
    }

    public static final class Builder {
        private List<Header> headers;
        private List<Param> params;
        private Body body;

        private Builder() {
        }

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

        public Builder withBody(String value) {
            this.body = new Body(value);
            return this;
        }

        public HttpRequest build() {
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.params = this.params;
            httpRequest.headers = this.headers;
            httpRequest.body = this.body;
            return httpRequest;
        }
    }
}
