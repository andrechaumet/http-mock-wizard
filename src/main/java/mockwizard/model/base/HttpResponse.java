package mockwizard.model.base;

import mockwizard.model.component.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private Integer httpStatusCode;
    private List<Header> headers;
    private String body;

    public Map<String, List<String>> getHeadersAsResponse() {
        final Map<String, List<String>> responseHeaders = new HashMap<>();
        for (Header header : headers) {
            String key = header.getKey();
            List<String> values = header.getValues();
            if (key != null && values != null) {
                responseHeaders.put(key, values);
            }
        }
        return responseHeaders;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public static final class Builder {
        private Integer httpStatusCode;
        private List<Header> headers;
        private String body;

        private Builder() {
        }

        public static Builder aHttpResponse() {
            return new Builder();
        }

        public Builder withHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder withHeaders(List<Header> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.body = this.body;
            httpResponse.headers = this.headers;
            httpResponse.httpStatusCode = this.httpStatusCode;
            return httpResponse;
        }
    }
}
